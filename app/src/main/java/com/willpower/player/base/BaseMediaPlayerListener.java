/**
 * Android Jungle-MediaPlayer framework project.
 *
 * Copyright 2016 Arno Zhang <zyfgood12@163.com>
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

package com.willpower.player.base;

public interface BaseMediaPlayerListener {

    void onLoading();

    void onLoadFailed();

    void onFinishLoading();

    void onError(int what, boolean canReload, String message);

    void onStartPlay();

    void onPlayComplete();

    void onStartSeek();

    void onSeekComplete();

    void onResumed();

    void onPaused();

    void onStopped();
}
