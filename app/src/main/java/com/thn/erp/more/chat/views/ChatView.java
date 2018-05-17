package com.thn.erp.more.chat.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.thn.chatinput.ChatInputView;
import com.thn.chatinput.listener.OnCameraCallbackListener;
import com.thn.chatinput.listener.OnClickEditTextListener;
import com.thn.chatinput.listener.OnMenuClickListener;
import com.thn.chatinput.listener.RecordVoiceListener;
import com.thn.chatinput.record.RecordVoiceButton;
import com.thn.erp.R;
import com.thn.erp.view.CustomHeadView;
import com.thn.messagelist.messages.MessageList;
import com.thn.messagelist.messages.MsgListAdapter;
import com.thn.messagelist.messages.ptr.PtrDefaultHeader;
import com.thn.messagelist.messages.ptr.PullToRefreshLayout;
import com.thn.messagelist.utils.DisplayUtil;


public class ChatView extends RelativeLayout {

    private CustomHeadView customHeadView;
    private MessageList mMsgList;
    private ChatInputView mChatInput;
    private RecordVoiceButton mRecordVoiceBtn;
    private PullToRefreshLayout mPtrLayout;
    private ImageButton mSelectAlbumIb;
    private ImageButton mAddPicBtn;
    public ChatView(Context context) {
        super(context);
    }

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initModule() {
        customHeadView = findViewById(R.id.customHeadView);

        mMsgList = findViewById(R.id.msg_list);
        mChatInput = findViewById(R.id.chat_input);
        mPtrLayout = findViewById(R.id.pull_to_refresh_layout);

        /**
         * Should set menu container height once the ChatInputView has been initialized.
         * For perfect display, the height should be equals with soft input height.
         */
        mChatInput.setMenuContainerHeight(819);
        mAddPicBtn = mChatInput.getAddPicBtn();
        mRecordVoiceBtn = mChatInput.getRecordVoiceButton();
        mSelectAlbumIb = mChatInput.getSelectAlbumBtn();
        PtrDefaultHeader header = new PtrDefaultHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new LayoutParams(-1, -2));
        header.setPadding(0, DisplayUtil.dp2px(getContext(), 15), 0,
                DisplayUtil.dp2px(getContext(), 10));
        header.setPtrFrameLayout(mPtrLayout);
//        mMsgList.setDateBgColor(Color.parseColor("#FF4081"));
//        mMsgList.setDatePadding(5, 10, 10, 5);
//        mMsgList.setEventTextPadding(5);
//        mMsgList.setEventBgColor(Color.parseColor("#34A350"));
//        mMsgList.setDateBgCornerRadius(15);
        mMsgList.setHasFixedSize(true);
        mPtrLayout.setLoadingMinTime(1000);
        mPtrLayout.setDurationToCloseHeader(1500);
        mPtrLayout.setHeaderView(header);
        mPtrLayout.addPtrUIHandler(header);
        // 下拉刷新时，内容固定，只有 Header 变化
        mPtrLayout.setPinContent(true);
        // set show display name or not
//        mMsgList.setShowReceiverDisplayName(true);
//        mMsgList.setShowSenderDisplayName(false);
    }

    public PullToRefreshLayout getPtrLayout() {
        return mPtrLayout;
    }

    public void setTitle(String title) {
        customHeadView.setCenterTxtShow(title, getResources().getColor(android.R.color.white));
    }

    public void setMenuClickListener(OnMenuClickListener listener) {
        mChatInput.setMenuClickListener(listener);
    }

    public void setAdapter(MsgListAdapter adapter) {
        mMsgList.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mMsgList.setLayoutManager(layoutManager);
    }

    public void setRecordVoiceFile(String path, String fileName) {
        mRecordVoiceBtn.setVoiceFilePath(path, fileName);
    }

    public void setCameraCaptureFile(String path, String fileName) {
        mChatInput.setCameraCaptureFile(path, fileName);
    }

    public void setRecordVoiceListener(RecordVoiceListener listener) {
        mChatInput.setRecordVoiceListener(listener);
    }

    public void setOnCameraCallbackListener(OnCameraCallbackListener listener) {
        mChatInput.setOnCameraCallbackListener(listener);
    }

    public void setOnTouchListener(OnTouchListener listener) {
        mMsgList.setOnTouchListener(listener);
    }

    public void setOnTouchEditTextListener(OnClickEditTextListener listener) {
        mChatInput.setOnClickEditTextListener(listener);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public ChatInputView getChatInputView() {
        return mChatInput;
    }

    public MessageList getMessageListView() {
        return mMsgList;
    }

    public ImageButton getSelectAlbumBtn() {
        return this.mSelectAlbumIb;
    }

    public ImageButton getAddPicBtn() {
        return  this.mAddPicBtn;
    }
}
