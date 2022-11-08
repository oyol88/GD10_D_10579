package com.example.gd10_d_10579

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.gd10_d_10579.databinding.ActivityDetailMahasiswaBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DetailMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailMahasiswaBinding
    private var b:Bundle? = null
    private val listMahasiswa = ArrayList<MahasiswaData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityDetailMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        b = intent.extras
        val nim = b?.getString("nim")
        nim?.let { getDataDetail(it) }
        binding.btnHapus.setOnClickListener {
            nim?.let { it1 -> deleteData(it1) }
        }
        binding.btnEdit.setOnClickListener {
            startActivity(
                Intent(this,
                FormEditMahasiswaActivity::class.java).apply {
                putExtra("nim",nim)
            })
        }
    }
    fun getDataDetail(nim:String){
        RClient.instances.getData(nim).enqueue(object : retrofit2.Callback<ResponseDataMahasiswa>
        {
            override fun onResponse(call: Call<ResponseDataMahasiswa>,response: Response<ResponseDataMahasiswa>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        listMahasiswa.addAll(it.data) }
                    with(binding) {
                        tvNim.text = listMahasiswa[0].nim
                        tvNama.text = listMahasiswa[0].nama
                        tvAlamat.text = listMahasiswa[0].alamat
                        tvProdi.text = listMahasiswa[0].prodi
                        tvTgllahir.text = listMahasiswa[0].tgllhr
                    }
                }
            }
            override fun onFailure(call:
                                   Call<ResponseDataMahasiswa>, t: Throwable) {
            }
        })
    }
    override fun onRestart() {
        super.onRestart()
        this.recreate()
    }
    fun deleteData(nim:String){
        val builder =
            AlertDialog.Builder(this@DetailMahasiswaActivity)
        builder.setMessage("Anda Yakin mau hapus?? Saya ngga yakin loh.")
            .setCancelable(false)
            .setPositiveButton("Ya, Hapus Aja!"){dialog, id->
                doDeleteData(nim)
            }
            .setNegativeButton("Tidak, Masih sayang dataku"){dialog,id ->
                    dialog.dismiss()
    }
    val alert = builder.create()
    alert.show()
}
private fun doDeleteData(nim:String) {
    RClient.instances.deleteData(nim).enqueue(object : retrofit2.Callback<ResponseCreate>{
        override fun onResponse(call: Call<ResponseCreate>, response: Response<ResponseCreate>) {
            TODO("Not yet implemented")
        }

        override fun onFailure(call: Call<ResponseCreate>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
}
}
