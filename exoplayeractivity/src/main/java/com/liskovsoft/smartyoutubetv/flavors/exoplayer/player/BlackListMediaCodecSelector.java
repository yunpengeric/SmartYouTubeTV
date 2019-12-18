package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.ArrayList;
import java.util.List;

public class BlackListMediaCodecSelector implements MediaCodecSelector {
    private static final String TAG = BlackListMediaCodecSelector.class.getSimpleName();

    // list of strings used in blacklisting codecs
    final static String[] BLACKLISTEDCODECS = {"OMX.google.h264.decoder", "OMX.Nvidia.vp9.decoder", "OMX.google.vp9.decoder", "OMX.MTK.VIDEO.DECODER.VP9", "OMX.amlogic.vp9.decoder"};

    @Override
    public List<MediaCodecInfo> getDecoderInfos(String mimeType, boolean requiresSecureDecoder) throws MediaCodecUtil.DecoderQueryException {

        List<MediaCodecInfo> codecInfos = MediaCodecUtil.getDecoderInfos(
                mimeType, requiresSecureDecoder);
        // filter codecs based on blacklist template
        List<MediaCodecInfo> filteredCodecInfos = new ArrayList<>();
        for (MediaCodecInfo codecInfo: codecInfos) {
            Log.d(TAG, "Checking codec: " + codecInfo);
            boolean blacklisted = false;
            for (String blackListedCodec: BLACKLISTEDCODECS) {
                if (codecInfo != null && codecInfo.name.toLowerCase().contains(blackListedCodec.toLowerCase())) {
                    Log.d(TAG, "Blacklisting codec: " + blackListedCodec);
                    blacklisted = true;
                    break;
                }
            }
            if (!blacklisted) {
                filteredCodecInfos.add(codecInfo);
            }
        }
        return filteredCodecInfos;
    }

    @Nullable
    @Override
    public MediaCodecInfo getPassthroughDecoderInfo() throws MediaCodecUtil.DecoderQueryException {
        return MediaCodecUtil.getPassthroughDecoderInfo();
    }
}