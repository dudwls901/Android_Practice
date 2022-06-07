package fastcampus.chapter1.fastcampus_galleryframe

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {


    private val startPhotoBtn : Button by lazy{
        findViewById<Button>(R.id.startPhotoFrameModeBtn)
    }
    private val addPhotoBtn : Button by lazy{
        findViewById<Button>(R.id.addPhotoBtn)
    }

    private val imageViewList : List<ImageView> by lazy{
        mutableListOf<ImageView>().apply{
            add(findViewById(R.id.imageView11))
            add(findViewById(R.id.imageView12))
            add(findViewById(R.id.imageView13))
            add(findViewById(R.id.imageView21))
            add(findViewById(R.id.imageView22))
            add(findViewById(R.id.imageView23))
        }
    }

    private val imageUriList = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        initAddPhotoButton()
        initStartPhotoFrameModeButton()
    }

    //onCreate 내의 코드를 줄이기 위해 함수로
    private fun initAddPhotoButton() {
        addPhotoBtn.setOnClickListener {
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE //저장소 권한
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //권한이 잘 부여되었을 때 갤러리에서 사진을 선택하는 기능
                    navigatePhotos()
                }
                //권한이 부여되어있지 않다면 교육용 팝업을 띄어야 하는지 확인 후 권한 팝업 띄우기
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) ->{
                    showPermissionContextPopup()
                }
                //교육용 팝업도, 권한도 없는 경우
                else ->{
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
                }
            }
        }
    }

    private fun initStartPhotoFrameModeButton(){

        startPhotoBtn.setOnClickListener { 
            val intent = Intent(this, PhotoFrameActivity::class.java)

                imageUriList.forEachIndexed { index, uri ->
                    intent.putExtra("photo$index",uri.toString()) }
            intent.putExtra("photoListSize",imageUriList.size)
            startActivity(intent)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //권한이 부여된 것
                    navigatePhotos()
                }
                else{
                    Toast.makeText(this, "권한을 거부하셨습니다.",Toast.LENGTH_SHORT).show()
                }
            }

            else ->{
                //권한이 부여되지 않은 경우는 에러밖에 없음
            }
        }
    }

    // 컨텐츠 프로바이더에서 saf 기능을 통해 사진을 가져오는 것은 코드가 간결하고 사용자에게도 친화적임
    //saf를 통해 띄어지는 액티비티는 다른 앱에서도 똑같기 때문에 유저에게 익숙
    //컨텐츠 프로바이더에서 saf 기능 말고도 직접 커스텀하여 갤러리의 사진을 가져오는 방법이 있으나 saf는 고정이기 때문에 익숙
     private fun navigatePhotos(){
        //action_get_content saf기능 실행 시켜서 컨텐츠를 가져오는 안드로이드 내장 액티비티 실행
        //
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        //이미지 타입만 가져오기
        //이 intent(saf 기능)도 다른 액티비티를 실행하고 선택된 컨텐츠를 받아와야 하기 때문에 콜백을 통해 받아와야 하므로 그에 대한 키값으로 2000
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK){
            return
        }
        //resultok일때만 실행
        when(requestCode){
            2000 ->{
                val selectedimageUri: Uri? = data?.data
                if(selectedimageUri != null){

                    if(imageUriList.size ==6){
                        Toast.makeText(this, "이미 사진이 꽉 찼습니다.",Toast.LENGTH_SHORT).show()
                    }
                    //오토캐스팅 null이 아님
                    imageUriList.add(selectedimageUri)
                    imageViewList[imageUriList.size-1].setImageURI(selectedimageUri)
                }
                else{
                    Toast.makeText(this, "사진을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show()
                }
            }
            else->{
                Toast.makeText(this, "사진을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup(){
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("전자액자에 앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }.setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

}