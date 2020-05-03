/*
 * Copyright (C) 2008-2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.kkamalyesh.apps.stk.virtualkeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;


public class KKeyboardEn extends Keyboard {

    private Key mEnterKey;
    private Key mSpaceKey;
    //private Key mSavedSpaceKey;
    /**
     * Stores the current state of the mode change key. Its width will be dynamically updated to
     * match the region of {@link #mModeChangeKey} when {@link #mModeChangeKey} becomes invisible.
     */
    private Key mModeChangeKey;
    /**
     * Stores the current state of the language switch key (a.k.a. globe key). This should be
     * visible while {@link InputMethodManager#//shouldOfferSwitchingToNextInputMethod(IBinder)}
     * returns true. When this key becomes invisible, its width will be shrunk to zero.
     */
    //private Key mLanguageSwitchKey;
    /**
     * Stores the size and other information of {@link #mModeChangeKey} when
     * {@link #mLanguageSwitchKey} is visible. This should be immutable and will be used only as a
     * reference size when the visibility of {@link #mLanguageSwitchKey} is changed.
     */
    //private Key mSavedModeChangeKey;
    /**
     * Stores the size and other information of { @link #mLanguageSwitchKey} when it is visible.
     * This should be immutable and will be used only as a reference size when the visibility of
     * { @link #mLanguageSwitchKey} is changed.
     */
    //private Key mSavedLanguageSwitchKey;

    private Key mKeySuggestionModeSwitchKey;
    private Key mSavedKeySuggestionModeSwitchKey;


    public KKeyboardEn(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public KKeyboardEn(Context context, int layoutTemplateResId,
                       CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, 
            XmlResourceParser parser) {
        Key key = new KKeyEn(res, parent, x, y, parser);
        key.popupCharacters=key.label;
        if (key.codes[0] == 10) {
            mEnterKey = key;
        } else if (key.codes[0] == KeyEvent.KEYCODE_SPACE) {
            mSpaceKey = key;
            //mSavedSpaceKey= new KKeyEn(res, parent, x, y, parser);
        } else if (key.codes[0] == Keyboard.KEYCODE_MODE_CHANGE) {
            mModeChangeKey = key;
            //mSavedModeChangeKey = new KKeyEn(res, parent, x, y, parser);
        } /*else if (key.codes[0] == KKeyboardEnView.KEYCODE_LANGUAGE_SWITCH) {
            mLanguageSwitchKey = key;
            mSavedLanguageSwitchKey = new KKeyEn(res, parent, x, y, parser);
        }*/ else if(key.codes[0] == KKeyboardEnView.KEYCODE_TOGGLE_KEY_SUGGESTIONS_MODE){
            mKeySuggestionModeSwitchKey = key;
            mSavedKeySuggestionModeSwitchKey = new KKeyEn(res, parent, x, y, parser);
        }
        return key;
    }

    /**
     * Dynamically change the visibility of the language switch key (a.k.a. globe key).
     * @param visible True if the language switch key should be visible.
     */
    /*void setLanguageSwitchKeyVisibility(boolean visible) {
        if (visible) {
            // The language switch key should be visible. Restore the size of the space/mode-change key
            // and language switch key using the saved layout.
            //mModeChangeKey.width = mSavedModeChangeKey.width;
            //mModeChangeKey.x = mSavedModeChangeKey.x;
            mSpaceKey.width = mSavedSpaceKey.width;
            mSpaceKey.x = mSavedSpaceKey.x;
            mLanguageSwitchKey.width = mSavedLanguageSwitchKey.width;
            mLanguageSwitchKey.icon = mSavedLanguageSwitchKey.icon;
            mLanguageSwitchKey.iconPreview = mSavedLanguageSwitchKey.iconPreview;
        } else {
            // The language switch key should be hidden. Change the width of the mode change key
            // to fill the space of the language key so that the user will not see any strange gap.
            mSpaceKey.width = mSavedSpaceKey.width + mSavedLanguageSwitchKey.width;
            mLanguageSwitchKey.width = 0;
            mLanguageSwitchKey.icon = null;
            mLanguageSwitchKey.iconPreview = null;
            mLanguageSwitchKey.text="";
            mLanguageSwitchKey.label="";
        }
    }*/

    /**
     * This looks at the ime options given by the current editor, to set the
     * appropriate label on the keyboard's enter key (if it has one).
     */
    void setImeOptions(Resources res, int options) {
        if (mEnterKey == null) {
            return;
        }

        switch (options&(EditorInfo.IME_MASK_ACTION|EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
            case EditorInfo.IME_ACTION_GO:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                mEnterKey.label = res.getText(R.string.label_go_key);
                break;
            case EditorInfo.IME_ACTION_NEXT:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                mEnterKey.label = res.getText(R.string.label_next_key);
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                mEnterKey.label = res.getText(R.string.label_find_key);
                break;
            case EditorInfo.IME_ACTION_SEND:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                mEnterKey.label = res.getText(R.string.label_send_key);
                break;
            default:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                mEnterKey.label = res.getText(R.string.label_ret_key);
                break;
        }
    }

    void setSpaceIcon(final Drawable icon) {
        if (mSpaceKey != null) {
            mSpaceKey.icon = icon;
        }
    }

    String kOn = "क", kOff = "क.";
    public void switchKeySuggestionModeKeyState(Resources resources){
        if(isKeySuggestionModeKeyStateOn(resources)){
            switchKeySuggestionModeKeyStateOff(resources);
        }else{
            mKeySuggestionModeSwitchKey.label = kOn;
        }
    }

    public void switchKeySuggestionModeKeyStateOff(Resources resources){
        mKeySuggestionModeSwitchKey.label= kOff;
    }

    public boolean isKeySuggestionModeKeyStateOn(Resources resources){

        if(mKeySuggestionModeSwitchKey.label.toString().equals(kOn)){
            return true;
        }
        return false;

    }

    public ArrayList<KKeyEn> getKKeys() {
        List<Key> from = getKeys();
        ArrayList<KKeyEn> to = new ArrayList<KKeyEn>();
        for(int i=0; i<from.size(); ++i){
            to.add((KKeyEn)from.get(i));
        }
        return to;
    }

    static public boolean isAlphabet(int c){
        if(c == KKeyboardEnView.KEYCODE_LANGUAGE_SWITCH) {
            return false;
        }
        if(c == KKeyboardEnView.KEYCODE_OPTIONS){
            return false;
        }
        if(c == KKeyboardEn.KEYCODE_CANCEL) {
            return false;
        }
        if(c == KKeyboardEn.KEYCODE_DELETE) {
            return false;
        }
        if(c == KKeyboardEn.KEYCODE_DONE){
            return false;
        }
        if(c == KKeyboardEn.KEYCODE_MODE_CHANGE){
            return false;
        }
        if(c == KKeyboardEn.KEYCODE_SHIFT){
            return false;
        }
        if(Character.isLetter(c)){
            return true;
        }else {
            return false;
        }
    }

    static class KKeyEn extends Key {

        private final CharSequence copyLabel;
        private final int[] copyCodes;
        private final CharSequence copyText;

        public KKeyEn(Resources res, Row parent, int x, int y,
                      XmlResourceParser parser) {
            super(res, parent, x, y, parser);

            copyLabel = this.label;
            copyCodes = this.codes;
            copyText = this.text;
        }

        public void hide(){
            label = null;
            text = null;
            codes = null;
        }

        public void undo(){
            label = copyLabel;
            text = copyText;
            codes = copyCodes;
        }

        /**
         * Overriding this method so that we can reduce the target area for the key that
         * closes the keyboard. 
         */
        @Override
        public boolean isInside(int x, int y) {
            if(label!=null) {
                //  keys with icons but no label will have label as ""
                //  keys which do not have icons, and are hidden will have null label
                //  -kk
                return super.isInside(x, codes[0] == KEYCODE_CANCEL ? y - 10 : y);
            }else{
                return false;
            }
        }

        public int getCode() {
            return copyCodes[0];
        }

        public CharSequence getLabel() {
            return label;
        }
    }



}
