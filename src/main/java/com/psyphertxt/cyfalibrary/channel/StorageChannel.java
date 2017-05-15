package com.psyphertxt.cyfalibrary.channel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.psyphertxt.cyfalibrary.Config;
import com.psyphertxt.cyfalibrary.Prefs;
import com.psyphertxt.cyfalibrary.listeners.CallbackListener;
import com.psyphertxt.cyfalibrary.models.ErrorCodes;
import com.psyphertxt.cyfalibrary.models.Profile;
import com.psyphertxt.cyfalibrary.utils.SecurityUtils;
import com.psyphertxt.cyfalibrary.utils.TextUtils;
import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;

/**
 * manages the storage layer of the application
 * downloading files, uploading files and storing
 * files on the device
 */

//===============  Media Sharing ==============//
public class StorageChannel {

    private static final String DIR_MAIN = "com.psyphertxt.cyfalibrary.Cyfa/";
    private static final String DIR_QRCODE = "QR Codes";
    private static final String DIR_PROFILE_PICTURES = "Profile Pictures/";
    private static final String DIR_IMAGES = "Images/";
    private static final String DIR_VIDEOS = "Videos/";
    //realtime database references
    private static final String REF_DIR_PROFILE_PICTURES = "bold-sea-2228-profile-pictures";
    private static final String REF_DIR_IMAGES = "late-wave-3216-images";
    private static final String REF_DIR_VIDEOS = "twilight-feather-6343-videos";
    private static final String KEY_JPEG = ".jpeg";
    private static final String KEY_PNG = ".png";
    private static final String KEY_MP4 = ".mp4";
    private StorageReference storageRef;
    private StorageReference directoryRef;
    private StorageReference imageRef;
    private String location = Config.EMPTY_STRING;
    private String imageLocation = Config.EMPTY_STRING;
    private String extension = Config.EMPTY_STRING;
    private Bitmap bitmap;
    private String fileName;
    private byte[] bitmapData;
    private Storage storage;

    public StorageChannel(Context context) {
        storage = null;
        if (SimpleStorage.isExternalStorageWritable()) {
            storage = SimpleStorage.getExternalStorage();
        } else {
            storage = SimpleStorage.getInternalStorage(context);
        }
    }

    public static Bitmap decodeBitmapFromPath(Context context, String path) {
        try {
            Bitmap compressedBitmap = Compressor.getDefault(context).compressToBitmap(new File(path));
            return compressedBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getProfileImage(final Context context, final StorageChannel storageChannel, final Profile profile, final CallbackListener.onFileCompletionListener listener) {

        listener.before(context);

        Prefs prefs = new Prefs(context);

        if (prefs.getCameraPermission()) {

            if (profile.getImageName() != null &&
                    !prefs.getProfileImageName().equals(Config.KEY_DEFAULT)) {

                //check if the users profile exist on the local device
                if (storageChannel != null) {

                    final File imageFile = storageChannel.
                            name(profile.getImageName()).
                            profile(profile.getUserId()).
                            jpeg().
                            image().
                            file();

                    if (imageFile != null) {
                        listener.success(imageFile);
                    } else {

                        //try downloading the users profile from the server
                        storageChannel.download(true, new CallbackListener.completion() {
                            @Override
                            public void done() {

                                listener.success(storageChannel.file());
                            }
                        });
                    }
                }else{
                    listener.error(ErrorCodes.RESOURCE_FAILED_ERROR.toString());
                }
            }
        }else{
            listener.error(ErrorCodes.RESOURCE_FAILED_ERROR.toString());
        }
    }

    public static void getProfileImageFromUserId(final Context context, final StorageChannel storageChannel, final String profile, String userId, final CallbackListener.onFileCompletionListener listener) {

        listener.before(context);

        Prefs prefs = new Prefs(context);

        if (prefs.getCameraPermission()) {

           //check if the users profile exist on the local device
            if (storageChannel != null) {

                final File imageFile = storageChannel.
                        name(profile).
                        profile(userId).
                        jpeg().
                        image().
                        file();

                if (imageFile != null) {
                    listener.success(imageFile);
                } else {

                    //try downloading the users profile from the server
                    storageChannel.download(true, new CallbackListener.completion() {
                        @Override
                        public void done() {

                            listener.success(storageChannel.file());
                        }
                    });
                }
            }else{
                listener.error(ErrorCodes.RESOURCE_FAILED_ERROR.toString());
            }
        }else{
            listener.error(ErrorCodes.RESOURCE_FAILED_ERROR.toString());
        }
    }

    public StorageChannel name(String filename) {
        storageRef = FirebaseStorage.getInstance().getReference();
        this.fileName = filename;
        return this;
    }

    public StorageChannel name() {
        storageRef = FirebaseStorage.getInstance().getReference();
        this.fileName = SecurityUtils.createMediaId();
        return this;
    }

    public StorageChannel profile(String userId) {
        this.location = DIR_PROFILE_PICTURES;
        if (storageRef != null) {
            this.directoryRef = storageRef.child(REF_DIR_PROFILE_PICTURES).child(userId);
        }
        return this;
    }

    public StorageChannel shareImage(String conversationId) {
        this.location = DIR_IMAGES;
        if (storageRef != null) {
            this.directoryRef = storageRef.child(REF_DIR_IMAGES).child(conversationId);
        }
        return this;
    }

    public StorageChannel shareVideo(String conversationId) {
        this.location = DIR_VIDEOS;
        if (storageRef != null) {
            this.directoryRef = storageRef.child(REF_DIR_VIDEOS).child(conversationId);
        }
        return this;
    }

    public StorageChannel bitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }

    public StorageChannel qrCode() {
        this.location = DIR_QRCODE;
        return this;
    }

    public StorageChannel jpeg() {
        this.extension = KEY_JPEG;
        return this;
    }

    public StorageChannel mp4() {
        this.extension = KEY_MP4;
        return this;
    }

    public StorageChannel png() {
        this.extension = KEY_PNG;
        return this;
    }

    public StorageChannel image() {

        //   this.imageLocation = KEY_IMAGE;
        if (directoryRef != null) {
            this.imageRef = directoryRef.child(fileName + extension);
        }
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            this.bitmapData = byteArrayOutputStream.toByteArray();
        }
        return this;
    }

    public StorageChannel video() {

        //   this.imageLocation = KEY_IMAGE;
        if (directoryRef != null) {
            this.imageRef = directoryRef.child(fileName + extension);
        }
        return this;
    }

    //===============  Media Sharing ==============//
    /* https://firebase.google.com/docs/storage/android/upload-files */
    public StorageChannel upload(final CallbackListener.callbackForResults callbackForResults) {

        if (imageRef == null && directoryRef == null && fileName == null && storageRef == null && bitmap == null && this.location == null) {
            throw new IllegalStateException(TextUtils.channelErrors("stream, directory, filename, storage, bitmap && location")); // thread-safe
        }

        ByteArrayInputStream stream = new ByteArrayInputStream(bitmapData);
        UploadTask uploadTask = imageRef.putStream(stream);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                callbackForResults.success(fileName);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callbackForResults.error(e.getMessage());
            }
        });

        return this;
    }


    //===============  Media Sharing ==============//
    /* https://firebase.google.com/docs/storage/android/download-files */
    public StorageChannel download(final boolean isImage, final CallbackListener.completion completion) {
        if (imageRef == null && directoryRef == null && fileName == null && storageRef == null && bitmap == null && this.location == null) {
            throw new IllegalStateException(TextUtils.channelErrors("directory, filename, storage, bitmap && location")); // thread-safe
        }

        if (imageRef != null) {

            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(final Uri uri) {

                    if (isImage) {
                        Log.d("IMAGE URI", uri.toString());
                    }
//
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        }
        return this;
    }

    public StorageChannel save() {
        if (fileName == null && bitmap == null && location == null) {
            throw new IllegalStateException(TextUtils.channelErrors("file name, bitmap && location")); // thread-safe
        }
        if (storage != null) {
            String location = DIR_MAIN + this.location + imageLocation;
            if (SimpleStorage.isExternalStorageWritable()) {
                if (bitmap != null) {
                    if (storage.isFileExist(location, fileName + extension)) {
                        storage.createFile(location, fileName + extension, bitmap);
                    } else {
                        storage.createDirectory(location);
                        storage.createFile(location, fileName + extension, bitmap);
                    }
                } else {
                    if (bitmapData != null) {
                        if (storage.isFileExist(location, fileName + extension)) {
                            storage.createFile(location, fileName + extension, bitmapData);
                        } else {
                            storage.createDirectory(location);
                            storage.createFile(location, fileName + extension, bitmapData);
                        }
                    }
                }
            }
        }
        return this;
    }

    public void delete() {
        if (fileName == null && this.location == null) {
            throw new IllegalStateException(TextUtils.channelErrors("file name && location")); // thread-safe
        }
        String location = DIR_MAIN + this.location + imageLocation;
        if (storage.isFileExist(location, fileName + extension)) {
            storage.deleteFile(location, fileName + extension);
        }
        if (imageRef != null) {
            imageRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //new Testable.Spec("Storage Channel").valueOf("file was deleted").expect(fileName).run();
                    } else {
                        //new Testable.Spec("Storage Channel").valueOf("file was not deleted").expect(fileName).run();
                    }
                }
            });
        }
    }


    //===============  Media Sharing ==============//

    public File file() {
        if (fileName == null && this.location == null) {
            throw new IllegalStateException(TextUtils.channelErrors("file name && location")); // thread-safe
        }
        if (storage != null) {
            String location = DIR_MAIN + this.location + imageLocation;
            if (storage.isFileExist(location, fileName + extension)) {
                List<File> files = storage.getFiles(location, fileName + extension);
                if (files.size() > Config.NUMBER_ZERO) {
                    for (File file : files) {
                        if (file.getName().equals(fileName + extension)) {
                            return file;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Bitmap read() {
        if (fileName == null && this.location == null) {
            throw new IllegalStateException(TextUtils.channelErrors("file name, bitmap && location")); // thread-safe
        }
        if (storage != null) {
            String location = DIR_MAIN + this.location + imageLocation;
            if (storage.isFileExist(location, fileName + extension)) {
                byte[] bytes = storage.readFile(location, fileName + extension);
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }
        return null;
    }

    public String getFileName() {
        return fileName;
    }

    public static class Converters {
        public static final int KILO_BYTES = 1024;

        public static double byteSToKB(long sizeInBytes) {
            return (double) sizeInBytes / KILO_BYTES;
        }

        public static double byteSToMB(long sizeInBytes) {
            return (double) sizeInBytes / Math.pow(KILO_BYTES, 2);
        }

        public static double byteSToGB(long sizeInBytes) {
            return (double) sizeInBytes / Math.pow(KILO_BYTES, 3);
        }

        public static double byteSToTB(long sizeInBytes) {
            return (double) sizeInBytes / Math.pow(KILO_BYTES, 4);
        }

        public static double mbToBytes(long sizeInMegaBytes) {
            return (double) sizeInMegaBytes * Math.pow(KILO_BYTES, 2);
        }

        public static String toReadableForm(long sizeInBytes) {
            if (sizeInBytes <= 0) return "0";
            final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
            int digitGroups = (int) (Math.log10(sizeInBytes) / Math.log10(1024));
            return new DecimalFormat("#,##0.#").format(sizeInBytes / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
        }
    }

    public static class Query {

        public static long getTotalSpace(File file) {
            return file.getTotalSpace();
        }

        public static long getAvailableSpace(File file) {
            return file.getFreeSpace();
        }

        public static boolean canAccomodate(long fileSizeInMegaBytes, File file) {
            return file.getFreeSpace() - Converters.mbToBytes(fileSizeInMegaBytes) >= 0;
        }
    }

}