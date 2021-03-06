/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.dao.UserDao;
import com.zsct.customer.model.User;
import com.zsct.customer.util.CommonUtil;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.ImageLoaderUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.PreferenceUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class PersonInfoActivity extends BaseActivity implements
		View.OnClickListener {
	protected static final int SELECT_PICTURE = 0;
	protected static final int SELECT_CAMER = 1;
	private LinearLayout updatePassword;
	private LinearLayout updateName;
	private LinearLayout updateSex;
	private LinearLayout updateHead;
	private ImageView image_head;
	private TextView text_phone;
	private TextView text_name;
	private TextView text_sex;
	private UserDao mUserDao;
	private User mUser;
	private String userid;
	private final int editmsg_what=1;
	private final int editimage_what=2;
	private Bitmap bmp;
	/*** 使用照相机拍照获取图片
	 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/***
	 * 使用相册中的图片
	 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

	private static final int CUT_PHOTO = 3;
	private Uri photoUri;
	/** 通过centerIndex来决定采用那种存储方式 **/
	private int centerIndex;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			closeLoadingDialog();
			String result = msg.obj.toString();
			Log.e("hjq", result);
			switch (msg.what) {
			case editmsg_what:
				try {
					JSONObject json = new JSONObject(result);
					if (json.getInt(JsonUtil.CODE) == 1) {
						mUserDao.update(mUser);
						showLongToast("修改完成");
						text_name.setText(mUser.getName());
						String sexstr = mUser.getSex().equals("1") ? "男" : "女";
						text_sex.setText(sexstr);
					} else {
						showLongToast(json.getString(JsonUtil.MSG));
						mUser = mUserDao.queryById(userid);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case editimage_what:
				try {
					JSONObject json = new JSONObject(result);
					if (json.getInt(JsonUtil.CODE) == 1) {
						 mUser=mUserDao.queryById(PreferenceUtil.getInstance(PersonInfoActivity.this).getUid());
                          if (mUser!=null) {
							mUser.setImage(json.getString(JsonUtil.IMAGE));
							mUser.setImage_thumb(json.getString(JsonUtil.IMAGE_THUMB));
							mUserDao.deleteById(mUser.getId());
							mUserDao.insert(mUser);
							ImageLoaderUtil.displayImage(HttpUtil.SERVER+mUser.getImage_thumb(), image_head, PersonInfoActivity.this);
						}else {
							
						}
					} else {
						showShortToast(json.getString(JsonUtil.MSG));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
			
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_information);
		findViewById(R.id.back).setOnClickListener(this);
		updatePassword = (LinearLayout) findViewById(R.id.personinformationPassword);
		updateHead = (LinearLayout) findViewById(R.id.personinformationPhoto);
		updateName = (LinearLayout) findViewById(R.id.personinformationName);
		updateSex = (LinearLayout) findViewById(R.id.personinformationSex);
		image_head = (ImageView) findViewById(R.id.headimage);
		text_phone = (TextView) findViewById(R.id.text_phone);
		text_name = (TextView) findViewById(R.id.text_name);
		text_sex = (TextView) findViewById(R.id.text_sex);
		updatePassword.setOnClickListener(this);
		updateHead.setOnClickListener(this);
		updateName.setOnClickListener(this);
		updateSex.setOnClickListener(this);
		mUserDao = new UserDao(this);
		userid = PreferenceUtil.getInstance(this).getUid();
		mUser = mUserDao.queryById(userid);
		mUser=new UserDao(this).queryById(PreferenceUtil.getInstance(this).getUid());
		text_phone.setText(mUser.getPhone());
		text_name.setText(mUser.getName());
		String sexstr = mUser.getSex().equals("1") ? "男" : "女";
		text_sex.setText(sexstr);
		if (mUser.getImage().equals("")) {
			image_head.setImageResource(R.drawable.null_user);
		}else {
			ImageLoaderUtil.displayImage(HttpUtil.SERVER+mUser.getImage_thumb(), image_head, this);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.personinformationPhoto:
			getimage();
			break;
		case R.id.personinformationPassword:
			startActivity(new Intent(this, PersonUpdatePasswordActivity.class));
			break;
		case R.id.personinformationName:
			final EditText nameedit = new EditText(this);
			nameedit.setText(mUser.getName());
			AlertDialog.Builder namebuilder = new AlertDialog.Builder(this);
			namebuilder.setTitle("修改用户名")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(nameedit)
					.setNegativeButton(R.string.system_cancel, null);
			namebuilder
					.setPositiveButton(R.string.system_sure,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									mUser.setName(nameedit.getText().toString()
											.trim());
									UpdateUserInfo(mUser);
								}
							}).create().show();
			break;
		case R.id.personinformationSex:
			final RadioGroup sexgroup = new RadioGroup(this);
			RadioButton radioBtn1 = new RadioButton(this);
			radioBtn1.setId(1);
			radioBtn1.setText("男");
			radioBtn1.setTextColor(Color.WHITE);
			RadioButton radioBtn2 = new RadioButton(this);
			radioBtn2.setId(0);
			radioBtn2.setText("女");
			radioBtn2.setTextColor(Color.WHITE);
			sexgroup.addView(radioBtn1);
			sexgroup.addView(radioBtn2);
			if (mUser.getSex().equals("1")) {
				sexgroup.check(1);
			} else {
				sexgroup.check(0);
			}
			AlertDialog.Builder sexbuilder = new AlertDialog.Builder(this);
			sexbuilder.setTitle("修改性别")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(sexgroup)
					.setNegativeButton(R.string.system_cancel, null);
			sexbuilder
					.setPositiveButton(R.string.system_sure,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									String id = String.valueOf(sexgroup
											.getCheckedRadioButtonId());
									if (!mUser.getSex().equals(id)) {
										mUser.setSex(id);
										UpdateUserInfo(mUser);
									}

								}
							}).create().show();
			break;
		default:
			break;
		}
	}

	private void UpdateUserInfo(final User user) {
		showLoadingDialog();
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil.post(HttpUtil.URL_EDITPROFILE,
						new BasicNameValuePair(JsonUtil.NAME, user.getName()),
						new BasicNameValuePair(JsonUtil.SEX, user.getSex()),
						new BasicNameValuePair(JsonUtil.USER_ID, user.getId()));
				Message msg = new Message();
				msg.obj = result;
				msg.what=editmsg_what;
				mHandler.sendMessage(msg);
			}
		});
	}

	public void getimage() {
		CharSequence[] items = { "相册", "相机" }; // 设置显示选择框的内容
		new AlertDialog.Builder(this).setTitle("选择图片来源")
				.setItems(items, new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (which == SELECT_PICTURE) {
							Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							startActivityForResult(choosePictureIntent, SELECT_PIC_BY_PICK_PHOTO);
						} else {
							takePhoto();
						}
					}
				}).create().show();
	}

	private void takePhoto() {
		// TODO Auto-generated method stub
		// 执行拍照前，应该先判断SD卡是否存在
		String SDState = Environment.getExternalStorageState();
		if (!SDState.equals(Environment.MEDIA_MOUNTED)) {
			showShortToast("内存卡不存在");
			return;
		}
		try {
			photoUri = getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					new ContentValues());
			if (photoUri != null) {
				Intent i = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(i, SELECT_PIC_BY_TACK_PHOTO);

			} else {
				showShortToast("发生意外，无法写入相册");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			showShortToast("发生意外，无法写入相册");
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case SELECT_PIC_BY_TACK_PHOTO:
				// 选择自拍结果
				beginCrop(photoUri);
				break;
			case SELECT_PIC_BY_PICK_PHOTO:
				// 选择图库图片结果
				beginCrop(data.getData());
				break;
			case CUT_PHOTO:
				handleCrop(data);
				break;
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onChoosePhoto() {
		// TODO Auto-generated method stub
		// 从相册中取图片
		Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(choosePictureIntent, SELECT_PIC_BY_PICK_PHOTO);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void beginCrop(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高，注意如果return-data=true情况下,其实得到的是缩略图，并不是真实拍摄的图片大小，
		// 而原因是拍照的图片太大，所以这个宽高当你设置很大的时候发现并不起作用，就是因为返回的原图是缩略图，但是作为头像还是够清晰了
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		//返回图片数据
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CUT_PHOTO);
	}
	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param result
	 */
	private void handleCrop(Intent result) {
		Log.e("hjq", "handleCrop");
		Bundle extras = result.getExtras();
		if (extras != null) {
			 bmp = extras.getParcelable("data");
			try {
				CommonUtil.saveMyBitmap(bmp, "photo");
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.e("hjq", "run");
						Map<String, String> params=new HashMap<String, String>();
						params.put(JsonUtil.USER_ID, PreferenceUtil.getInstance(PersonInfoActivity.this).getUid());
						params.put(JsonUtil.IMAGE,Environment.getExternalStorageDirectory()
								+ "/peal_meal/photo.png");
						String str = "";
						try {
							str = CommonUtil.postForm(HttpUtil.URL_UPLOADUSERIMAGE, params);
							Log.e("hjq", str);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.e("hjq", e.getMessage());
						}
						Message msg=new Message();
						msg.obj=str;
						msg.what=editimage_what;
						mHandler.sendMessage(msg);
						
						
					}
				}).start();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
