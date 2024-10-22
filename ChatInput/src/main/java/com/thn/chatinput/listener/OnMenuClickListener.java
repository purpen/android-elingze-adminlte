package com.thn.chatinput.listener;


import com.thn.chatinput.model.FileItem;

import java.util.List;



/**
 * Menu items' callbacks
 */
public interface OnMenuClickListener {

    /**
     * Fires when send button is on click.
     *
     * @param input Input content
     * @return boolean
     */
    boolean onSendTextMessage(CharSequence input);

    /**
     * Files when send photos or videos.
     * When construct send message, you need to judge the type
     * of file item, according to
     *
     * @param list List of file item objects
     */
    void onSendFiles(List<FileItem> list);

    /**
     * Fires when voice button is on click.
     */
    boolean switchToMicrophoneMode();

    /**
     * Fires when photo button is on click.
     */
    boolean switchToGalleryMode();

    /**
     * Fires when camera button is on click.
     */
    boolean switchToCameraMode();

    /**
     * Fires when emoji button is on click.
     */
    boolean switchToEmojiMode();

    /**
     * Fires when add button is on click
     * @return
     */
    boolean switchToSelectMode();
}