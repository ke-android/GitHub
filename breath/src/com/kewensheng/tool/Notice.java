package com.kewensheng.tool;

import android.app.Service;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.kewensheng.breath.R;

/**��������ʱ������ʾ������*/
public class Notice {
	
	/**
	 * �𶯸�����
	 */
	public static void playBeepSoundAndVibrate(Context context,int id, int mill) {
		final MediaPlayer mp = new MediaPlayer();
		AssetFileDescriptor afd = context.getResources()
				.openRawResourceFd(id);
		try {
			mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			mp.setAudioStreamType(AudioManager.STREAM_RING);
			afd.close();
			mp.prepare();
			if (mp.isPlaying())
				mp.pause();
			mp.seekTo(mill);
			mp.setVolume(1500, 1500);// ��������
			mp.start();
			if (id == R.raw.notice)
				((Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE))
						.vibrate(new long[] { 100, 10, 100, 1000 }, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
