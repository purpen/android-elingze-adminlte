package com.thn.erp.more.chat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thn.basemodule.tools.Constants;
import com.thn.basemodule.tools.GlideUtil;
import com.thn.basemodule.tools.LogUtil;
import com.thn.chatinput.ChatInputView;
import com.thn.chatinput.listener.OnCameraCallbackListener;
import com.thn.chatinput.listener.OnMenuClickListener;
import com.thn.chatinput.listener.RecordVoiceListener;
import com.thn.chatinput.model.FileItem;
import com.thn.chatinput.model.VideoItem;
import com.thn.erp.R;
import com.thn.erp.album.ImageUtils;
import com.thn.erp.album.PicturePickerUtils;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.more.chat.beans.DefaultUser;
import com.thn.erp.more.chat.beans.MyMessage;
import com.thn.erp.more.chat.views.ChatView;
import com.thn.messagelist.commons.ImageLoader;
import com.thn.messagelist.commons.models.IMessage;
import com.thn.messagelist.messages.MsgListAdapter;
import com.thn.messagelist.messages.ViewHolderController;
import com.thn.messagelist.messages.ptr.PtrHandler;
import com.thn.messagelist.messages.ptr.PullToRefreshLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MessageListActivity extends BaseActivity implements View.OnTouchListener,
        EasyPermissions.PermissionCallbacks, SensorEventListener {
    private static final String Id0Avatar = "R.mipmap.icon_head_business";
    private static final String Id1Avatar = "R.mipmap.icon_head_business";
    private final int RC_RECORD_VOICE = 0x0001;

    private ChatView mChatView;
    private MsgListAdapter<MyMessage> mAdapter;
    private List<MyMessage> mData;

    private InputMethodManager mImm;
    private Window mWindow;
    private HeadsetDetectReceiver mReceiver;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    /**
     * Store all image messages' path, pass it to {@link BrowserImageActivity},
     * so that click image message can browser all images.
     */
    private ArrayList<String> mPathList = new ArrayList<>();
    private ArrayList<String> mMsgIdList = new ArrayList<>();
    private File mCurrentPhotoFile;

    @Override
    protected int getLayout() {
        return R.layout.activity_chat;
    }


    @Override
    protected void initView() {
        this.mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mWindow = getWindow();
        registerProximitySensorListener();
        mChatView = findViewById(R.id.chat_view);
        mChatView.initModule();
        mChatView.setTitle("在线服务");
        mData = getMessages();
        initMsgAdapter();
        mReceiver = new HeadsetDetectReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mReceiver, intentFilter);
    }


    @Override
    protected void installListener() {
        mChatView.getChatInputView().getmIdSelectAlbum().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    EasyPermissions.requestPermissions(activity, getString(R.string.rationale_external_storage),
                            Constants.REQUEST_CODE_PICK_IMAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    ImageUtils.getImageFromAlbum(activity, 9);
                }
            }
        });

        mChatView.getChatInputView().getmIdSelectCamera().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.CAMERA)) {
                    EasyPermissions.requestPermissions(activity, getString(R.string.rationale_external_storage),
                            Constants.REQUEST_CODE_CAPTURE_CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    openCamera();
                }
            }
        });


        mChatView.setOnTouchListener(this);

        mChatView.setMenuClickListener(new OnMenuClickListener() {
            @Override
            public boolean onSendTextMessage(CharSequence input) {
                if (input.length() == 0) {
                    return false;
                }
                MyMessage message = new MyMessage(input.toString(), IMessage.MessageType.SEND_TEXT.ordinal());
                message.setUserInfo(new DefaultUser("1", "客户", Id1Avatar));
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                message.setMessageStatus(IMessage.MessageStatus.SEND_GOING);
                mAdapter.addToStart(message, true);
                return true;
            }

            @Override
            public void onSendFiles(List<FileItem> list) {
                if (list == null || list.isEmpty()) {
                    return;
                }

                MyMessage message;
                for (FileItem item : list) {
                    if (item.getType() == FileItem.Type.Image) {
                        message = new MyMessage(null, IMessage.MessageType.SEND_IMAGE.ordinal());
                        mPathList.add(item.getFilePath());
                        mMsgIdList.add(message.getMsgId());
                    } else if (item.getType() == FileItem.Type.Video) {
                        message = new MyMessage(null, IMessage.MessageType.SEND_VIDEO.ordinal());
                        message.setDuration(((VideoItem) item).getDuration());

                    } else {
                        throw new RuntimeException("Invalid FileItem type. Must be Type.Image or Type.Video");
                    }

                    message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                    message.setMediaFilePath(item.getFilePath());
                    message.setUserInfo(new DefaultUser("1", "客户", Id1Avatar));

                    final MyMessage fMsg = message;
                    MessageListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addToStart(fMsg, true);
                        }
                    });
                }
            }

            @Override
            public boolean switchToSelectMode() {
                scrollToBottom();
                return true;
            }

            @Override
            public boolean switchToMicrophoneMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if (!EasyPermissions.hasPermissions(MessageListActivity.this, perms)) {
                    EasyPermissions.requestPermissions(MessageListActivity.this,
                            getResources().getString(R.string.rationale_record_voice),
                            RC_RECORD_VOICE, perms);
                }
                return true;
            }

            @Override
            public boolean switchToGalleryMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };

                if (!EasyPermissions.hasPermissions(MessageListActivity.this, perms)) {
                    EasyPermissions.requestPermissions(MessageListActivity.this,
                            getResources().getString(R.string.rationale_photo),
                            Constants.REQUEST_CODE_PICK_IMAGE, perms);
                }
                // If you call updateData, select photo view will try to update data(Last update over 30 seconds.)
                mChatView.getChatInputView().getSelectPhotoView().updateData();
                return true;
            }

            @Override
            public boolean switchToCameraMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                };

                if (!EasyPermissions.hasPermissions(MessageListActivity.this, perms)) {
                    EasyPermissions.requestPermissions(MessageListActivity.this,
                            getResources().getString(R.string.rationale_camera),
                            Constants.REQUEST_CODE_CAPTURE_CAMERA, perms);
                } else {
                    File rootDir = getFilesDir();
                    String fileDir = rootDir.getAbsolutePath() + "/photo";
                    mChatView.setCameraCaptureFile(fileDir, new SimpleDateFormat("yyyy-MM-dd-hhmmss",
                            Locale.getDefault()).format(new Date()));
                }
                return true;
            }

            @Override
            public boolean switchToEmojiMode() {
                scrollToBottom();
                return true;
            }
        });

        mChatView.setRecordVoiceListener(new RecordVoiceListener() {
            @Override
            public void onStartRecord() {
                // set voice file path, after recording, audio file will save here
                String path = Environment.getExternalStorageDirectory().getPath() + "/voice";
                File destDir = new File(path);
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                mChatView.setRecordVoiceFile(destDir.getPath(), DateFormat.format("yyyy-MM-dd-hhmmss",
                        Calendar.getInstance(Locale.CHINA)) + "");
            }

            @Override
            public void onFinishRecord(File voiceFile, int duration) {
                MyMessage message = new MyMessage(null, IMessage.MessageType.SEND_VOICE.ordinal());
                message.setUserInfo(new DefaultUser("1", "客户", Id1Avatar));
                message.setMediaFilePath(voiceFile.getPath());
                message.setDuration(duration);
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                mAdapter.addToStart(message, true);
            }

            @Override
            public void onCancelRecord() {

            }

            /**
             * In preview record voice layout, fires when click cancel button
             * Add since chatinput v0.7.3
             */
            @Override
            public void onPreviewCancel() {

            }

            /**
             * In preview record voice layout, fires when click send button
             * Add since chatinput v0.7.3
             */
            @Override
            public void onPreviewSend() {

            }
        });

        mChatView.setOnCameraCallbackListener(new OnCameraCallbackListener() {
            @Override
            public void onTakePictureCompleted(String photoPath) {
                final MyMessage message = new MyMessage(null, IMessage.MessageType.SEND_IMAGE.ordinal());
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                message.setMediaFilePath(photoPath);
                mPathList.add(photoPath);
                mMsgIdList.add(message.getMsgId());
                message.setUserInfo(new DefaultUser("1", "客户", Id1Avatar));
                MessageListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addToStart(message, true);
                    }
                });
            }

            @Override
            public void onStartVideoRecord() {

            }

            @Override
            public void onFinishVideoRecord(String videoPath) {

            }

            @Override
            public void onCancelVideoRecord() {

            }
        });

        //添加图片按钮点击滑动到底部
        mChatView.getAddPicBtn().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollToBottom();
                return false;
            }

        });

        mChatView.getChatInputView().getInputView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollToBottom();
                return false;
            }
        });
    }

    private void openCamera() {
        mCurrentPhotoFile = ImageUtils.getDefaultFile();
        if (null == mCurrentPhotoFile) return;
        ImageUtils.getImageFromCamera(activity, ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile));
    }


    private void registerProximitySensorListener() {
        try {
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        try {
            if (audioManager.isBluetoothA2dpOn() || audioManager.isWiredHeadsetOn()) {
                return;
            }
            if (mAdapter.getMediaPlayer().isPlaying()) {
                float distance = event.values[0];
                if (distance >= mSensor.getMaximumRange()) {
                    mAdapter.setAudioPlayByEarPhone(0);
                    setScreenOn();
                } else {
                    mAdapter.setAudioPlayByEarPhone(2);
                    ViewHolderController.getInstance().replayVoice();
                    setScreenOff();
                }
            } else {
                if (mWakeLock != null && mWakeLock.isHeld()) {
                    mWakeLock.release();
                    mWakeLock = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setScreenOn() {
        if (mWakeLock != null) {
            mWakeLock.setReferenceCounted(false);
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private void setScreenOff() {
        if (mWakeLock == null) {
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
        }
        mWakeLock.acquire();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class HeadsetDetectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                if (intent.hasExtra("state")) {
                    int state = intent.getIntExtra("state", 0);
                    mAdapter.setAudioPlayByEarPhone(state);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == Constants.REQUEST_CODE_PICK_IMAGE && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ImageUtils.getImageFromAlbum(activity, 9);
            LogUtil.e("onPermissionsGranted()::getImageFromAlbum");
        }

        if (requestCode == Constants.REQUEST_CODE_CAPTURE_CAMERA && perms.contains(Manifest.permission.CAMERA)) {
            openCamera();
            LogUtil.e("onPermissionsGranted()::openCamera");
        }
    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CODE_PICK_IMAGE:
                    List<Uri> mSelected = PicturePickerUtils.obtainResult(data);
                    if (mSelected == null || mSelected.size() == 0) return;
                    sendFileMessage(mSelected);
                    break;
                case Constants.REQUEST_CODE_CAPTURE_CAMERA:
                    if (null == mCurrentPhotoFile) return;
                    Uri uri = ImageUtils.getUriForFile(getApplicationContext(), mCurrentPhotoFile);
                    ArrayList<Uri> list = new ArrayList<>();
                    list.add(uri);
                    sendFileMessage(list);
                    break;
            }
        }
    }

    private List<FileItem> getFileItemList(List<Uri> uris) {
        if (uris == null || uris.isEmpty()) return null;
        List<FileItem> items = new ArrayList<>();
        FileItem fileItem;
        String path;
        for (Uri uri : uris) {
            path = ImageUtils.getRealFilePath(getApplicationContext(), uri);
            fileItem = new FileItem(path, "", "", "");
            fileItem.setType(FileItem.Type.Image);
            items.add(fileItem);
        }

        return items;
    }

    /**
     * 发送图片消息
     *
     * @param uris
     */
    private void sendFileMessage(List<Uri> uris) {
        if (uris == null || uris.isEmpty()) {
            return;
        }

        List<FileItem> fileItemList = getFileItemList(uris);

        MyMessage message;
        for (FileItem item : fileItemList) {
            if (item.getType() == FileItem.Type.Image) {
                message = new MyMessage(null, IMessage.MessageType.SEND_IMAGE.ordinal());
                mPathList.add(item.getFilePath());
                mMsgIdList.add(message.getMsgId());
            } else if (item.getType() == FileItem.Type.Video) {
                message = new MyMessage(null, IMessage.MessageType.SEND_VIDEO.ordinal());
                message.setDuration(((VideoItem) item).getDuration());

            } else {
                throw new RuntimeException("Invalid FileItem type. Must be Type.Image or Type.Video");
            }

            message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            message.setMediaFilePath(item.getFilePath());
            message.setUserInfo(new DefaultUser("1", "客户", Id1Avatar));

            final MyMessage fMsg = message;
            mAdapter.addToStart(fMsg, true);
        }
    }

    /**
     * 获取消息列表
     *
     * @return
     */
    private List<MyMessage> getMessages() {
        List<MyMessage> list = new ArrayList<>();
        Resources res = getResources();
        //假数据
        String[] messages = res.getStringArray(R.array.messages_array);
        for (int i = 0; i < messages.length; i++) {
            MyMessage message;
            if (i % 2 == 0) {
                message = new MyMessage(messages[i], IMessage.MessageType.RECEIVE_TEXT.ordinal());
                message.setUserInfo(new DefaultUser("0", "客户", Id0Avatar));
            } else {
                message = new MyMessage(messages[i], IMessage.MessageType.SEND_TEXT.ordinal());
                message.setUserInfo(new DefaultUser("1", "客服", Id1Avatar));
            }
            message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            list.add(message);
        }
        return list;
    }

    private void initMsgAdapter() {
        final float density = getResources().getDisplayMetrics().density;
//        final float MIN_WIDTH = 50 * density;
//        final float MAX_WIDTH = 150 * density;
//        final float MIN_HEIGHT = 60 * density;
//        final float MAX_HEIGHT = 200 * density;
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadAvatarImage(ImageView avatarImageView, String string) {
                // You can use other image load libraries.
                if (string.contains("R.mipmap")) {//加载项目图片
                    Integer resId = getResources().getIdentifier(string.replace("R.mipmap.", ""),
                            "mipmap", getPackageName());

                    avatarImageView.setImageResource(resId);
                } else {
                    GlideUtil.loadImage(string, avatarImageView);
                }
            }

            /**
             * Load image message
             * @param imageView Image message's ImageView.
             * @param string A file path, or a uri or url.
             */
            @Override
            public void loadImage(final ImageView imageView, final String string) {
                // You can use other image load libraries.
                GlideUtil.loadImage(string,imageView);

//                Glide.with(getApplicationContext())
//                        .asBitmap()
//                        .load(string)
//                        .into(new SimpleTarget<Bitmap>() {
//                            @Override
//                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                int imageWidth = resource.getWidth();
//                                int imageHeight = resource.getHeight();
//                                LogUtil.e(TAG + " imageWidth: " + imageWidth + " imageHeight: " + imageHeight);
//                                if (imageWidth <= MIN_WIDTH) {
//                                    GlideUtil.loadImageWithDimen(string, imageView, imageWidth, imageHeight);
//                                } else if (imageWidth > MAX_WIDTH) {
//                                    float scaleW = MAX_WIDTH;
//                                    float scaleH = imageHeight * MAX_WIDTH / imageWidth;
//                                    GlideUtil.loadImageWithDimen(string, imageView, (int) scaleW, (int) scaleH);
//                                }

//                                // 裁剪 bitmap
//                                float width, height;
//                                if (imageWidth > imageHeight) {
//                                    if (imageWidth > MAX_WIDTH) {
//                                        float temp = MAX_WIDTH / imageWidth * imageHeight;
//                                        height = temp > MIN_HEIGHT ? temp : MIN_HEIGHT;
//                                        width = MAX_WIDTH;
//                                    } else if (imageWidth < MIN_WIDTH) {
//                                        float temp = MIN_WIDTH / imageWidth * imageHeight;
//                                        height = temp < MAX_HEIGHT ? temp : MAX_HEIGHT;
//                                        width = MIN_WIDTH;
//                                    } else {
//                                        float ratio = imageWidth / imageHeight;
//                                        if (ratio > 3) {
//                                            ratio = 3;
//                                        }
//                                        height = imageHeight * ratio;
//                                        width = imageWidth;
//                                    }
//                                } else {
//                                    if (imageHeight > MAX_HEIGHT) {
//                                        float temp = MAX_HEIGHT / imageHeight * imageWidth;
//                                        width = temp > MIN_WIDTH ? temp : MIN_WIDTH;
//                                        height = MAX_HEIGHT;
//                                    } else if (imageHeight < MIN_HEIGHT) {
//                                        float temp = MIN_HEIGHT / imageHeight * imageWidth;
//                                        width = temp < MAX_WIDTH ? temp : MAX_WIDTH;
//                                        height = MIN_HEIGHT;
//                                    } else {
//                                        float ratio = imageHeight / imageWidth;
//                                        if (ratio > 3) {
//                                            ratio = 3;
//                                        }
//                                        width = imageWidth * ratio;
//                                        height = imageHeight;
//                                    }
//                                }
//                                ViewGroup.LayoutParams params = imageView.getLayoutParams();
//                                params.width = (int) width;
//                                params.height = (int) height;
//                                imageView.setLayoutParams(params);
//                                Matrix matrix = new Matrix();
//                                float scaleWidth = width / imageWidth;
//                                float scaleHeight = height / imageHeight;
//                                matrix.postScale(scaleWidth, scaleHeight);
//                                GlideUtil.loadImageWithDimen(string,imageView,(int)width,(int) height);
////                                imageView.setImageBitmap(Bitmap.createBitmap(resource, 0, 0, imageWidth, imageHeight, matrix, true));
//                            }
//                        });
            }

            /**
             * Load video message
             * @param imageCover Video message's image cover
             * @param uri Local path or url.
             */
            @Override
            public void loadVideo(ImageView imageCover, String uri) {
                long interval = 5000 * 1000;
                Glide.with(MessageListActivity.this)
                        .asBitmap()
                        .load(uri)
                        // Resize image view by change override size.
                        .apply(new RequestOptions().frame(interval).override(200, 400))
                        .into(imageCover);
            }
        };

        // Use default layout
        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();
        mAdapter = new MsgListAdapter<>("0", holdersConfig, imageLoader);
        // If you want to customise your layout, try to create custom ViewHolder:
        // holdersConfig.setSenderTxtMsg(CustomViewHolder.class, layoutRes);
        // holdersConfig.setReceiverTxtMsg(CustomViewHolder.class, layoutRes);
        // CustomViewHolder must extends ViewHolders defined in MsgListAdapter.
        // Current ViewHolders are TxtViewHolder, VoiceViewHolder.


        //点击事件
        mAdapter.setOnMsgClickListener(new MsgListAdapter.OnMsgClickListener<MyMessage>() {
            @Override
            public void onMessageClick(MyMessage message) {
                // do something
                if (message.getType() == IMessage.MessageType.RECEIVE_VIDEO.ordinal()
                        || message.getType() == IMessage.MessageType.SEND_VIDEO.ordinal()) {
                    if (!TextUtils.isEmpty(message.getMediaFilePath())) {
                        Intent intent = new Intent(MessageListActivity.this, VideoActivity.class);
                        intent.putExtra(VideoActivity.VIDEO_PATH, message.getMediaFilePath());
                        startActivity(intent);
                    }
                } else if (message.getType() == IMessage.MessageType.RECEIVE_IMAGE.ordinal()
                        || message.getType() == IMessage.MessageType.SEND_IMAGE.ordinal()) {
                    Intent intent = new Intent(MessageListActivity.this, BrowserImageActivity.class);
                    intent.putExtra("msgId", message.getMsgId());
                    intent.putStringArrayListExtra("pathList", mPathList);
                    intent.putStringArrayListExtra("idList", mMsgIdList);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getString(R.string.message_click_hint),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAdapter.setMsgLongClickListener(new MsgListAdapter.OnMsgLongClickListener<MyMessage>() {
            @Override
            public void onMessageLongClick(View view, MyMessage message) {
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.message_long_click_hint),
                        Toast.LENGTH_SHORT).show();
                // do something
            }
        });

        mAdapter.setOnAvatarClickListener(new MsgListAdapter.OnAvatarClickListener<MyMessage>() {
            @Override
            public void onAvatarClick(MyMessage message) {
                DefaultUser userInfo = (DefaultUser) message.getFromUser();
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.avatar_click_hint),
                        Toast.LENGTH_SHORT).show();
                // do something
            }
        });

        mAdapter.setMsgStatusViewClickListener(new MsgListAdapter.OnMsgStatusViewClickListener<MyMessage>() {
            @Override
            public void onStatusViewClick(MyMessage message) {
                // message status view click, resend or download here
            }
        });

        mChatView.getMessageListView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== RecyclerView.SCROLL_STATE_DRAGGING){
                    GlideUtil.pauseRequests(getApplicationContext());
                }

                if (newState== RecyclerView.SCROLL_STATE_IDLE){
                    GlideUtil.resumeRequests(getApplicationContext());
                }
            }
        });

        //TODO 添加首屏假数据
        MyMessage message = new MyMessage("Hello World", IMessage.MessageType.RECEIVE_TEXT.ordinal());
        message.setUserInfo(new DefaultUser("0", "客户", Id0Avatar));
        mAdapter.addToStart(message, true);
        MyMessage voiceMessage = new MyMessage("", IMessage.MessageType.RECEIVE_VOICE.ordinal());
        voiceMessage.setUserInfo(new DefaultUser("0", "客户", Id0Avatar));
        voiceMessage.setMediaFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/voice/2018-02-28-105103.m4a");
        voiceMessage.setDuration(4);
        mAdapter.addToStart(voiceMessage, true);

        MyMessage sendVoiceMsg = new MyMessage("", IMessage.MessageType.SEND_VOICE.ordinal());
        sendVoiceMsg.setUserInfo(new DefaultUser("1", "客服", Id1Avatar));
        sendVoiceMsg.setMediaFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/voice/2018-02-28-105103.m4a");
        sendVoiceMsg.setDuration(4);
        mAdapter.addToStart(sendVoiceMsg, true);

//        MyMessage eventMsg = new MyMessage("event", IMessage.MessageType.EVENT.ordinal());
//        mAdapter.addToStart(eventMsg, true);

        MyMessage receiveVideo = new MyMessage("", IMessage.MessageType.RECEIVE_VIDEO.ordinal());
        receiveVideo.setMediaFilePath(Environment.getExternalStorageDirectory().getPath() + "/Pictures/Hangouts/video-20170407_135638.3gp");
        receiveVideo.setDuration(4);
        receiveVideo.setUserInfo(new DefaultUser("0", "客户", Id0Avatar));
        mAdapter.addToStart(receiveVideo, true);
        mAdapter.addToEndChronologically(mData);

        PullToRefreshLayout layout = mChatView.getPtrLayout();
        layout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PullToRefreshLayout layout) {
                Log.i("MessageListActivity", "Loading next page");
                loadNextPage();
            }
        });
        // Deprecated, should use onRefreshBegin to load next page
        mAdapter.setOnLoadMoreListener(new MsgListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalCount) {
//                Log.i("MessageListActivity", "Loading next page");
//                loadNextPage();
            }
        });

        mChatView.setAdapter(mAdapter);
        mAdapter.getLayoutManager().scrollToPosition(0);
    }

    // 缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 加载下一页
     */
    private void loadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<MyMessage> list = new ArrayList<>();
                Resources res = getResources();
                String[] messages = res.getStringArray(R.array.conversation);
                for (int i = 0; i < messages.length; i++) {
                    MyMessage message;
                    if (i % 2 == 0) {
                        message = new MyMessage(messages[i], IMessage.MessageType.RECEIVE_TEXT.ordinal());
                        message.setUserInfo(new DefaultUser("0", "客户", Id0Avatar));
                    } else {
                        message = new MyMessage(messages[i], IMessage.MessageType.SEND_TEXT.ordinal());
                        message.setUserInfo(new DefaultUser("1", "客服", Id1Avatar));
                    }
                    message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                    list.add(message);
                }
//                Collections.reverse(list);
                // MessageList 0.7.2 add this method, add messages chronologically.
                mAdapter.addToEndChronologically(list);
                mChatView.getPtrLayout().refreshComplete();
            }
        }, 1000);
    }

    /**
     * 消息列表滑动到底部
     */
    public void scrollToBottom() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mChatView.getMessageListView().smoothScrollToPosition(0);
            }
        }, 250);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ChatInputView chatInputView = mChatView.getChatInputView();
                if (chatInputView.getMenuState() == View.VISIBLE) {
                    chatInputView.dismissMenuLayout();
                }
                try {
                    View v = getCurrentFocus();
                    if (mImm != null && v != null) {
                        mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        view.clearFocus();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_UP:
                view.performClick();
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mSensorManager.unregisterListener(this);
    }
}
