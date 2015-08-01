package com.kewensheng.breath;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kewensheng.tool.ToRound;

public class HomeFragment extends Fragment{
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private ImageView imageView4;
	private ImageView imageView5;
	private ImageView imageView6;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		imageView1 = (ImageView) getView().findViewById(R.id.image1);
		imageView2 = (ImageView) getView().findViewById(R.id.image2);
		imageView3 = (ImageView) getView().findViewById(R.id.image3);
		imageView4 = (ImageView) getView().findViewById(R.id.image4);
		imageView5 = (ImageView) getView().findViewById(R.id.image5);
		imageView6 = (ImageView) getView().findViewById(R.id.image6);
		 Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.kao);
	      Bitmap output=ToRound.toRoundCorner(bitmap, 120.0f);
	      imageView1.setImageBitmap(output);
	      imageView2.setImageBitmap(output);
	      imageView3.setImageBitmap(output);
	      imageView4.setImageBitmap(output);
	      imageView5.setImageBitmap(output);
	      imageView6.setImageBitmap(output);
	      
	}
}
