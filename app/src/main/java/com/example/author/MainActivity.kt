package com.example.author

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.author.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            val txtQuery = binding.edtSearch.text.toString()
            val maxRes = 10
            val printType = "books"
            if (txtQuery.isEmpty()) {
                binding.edtSearch.error = "Please fill the search box"
            } else {
                getData(txtQuery, maxRes, printType)
            }
        }
    }
    //berfungsi untuk memanggil method getdata, agar saat tombol cari ditekan data yang kita perlukan akan muncul.

    private fun getData(query: String, maxRes: Int, printType: String){
        isLoading(true)
        val client = ApiConfig.getApiServices().getBook(query, maxRes, printType)
        client.enqueue(object: Callback<BooksResponse> {
            override fun onResponse(call: Call<BooksResponse>, response: Response<BooksResponse>) {
                isLoading(false)
                if (response.isSuccessful) {
                    val title = response.body()?.items?.get(0)?.volumeInfo?.title
                    val author = response.body()?.items?.get(0)?.volumeInfo?.authors

                    binding.title.text = title
                    binding.authors.text = author.toString()
                }
            }
            //onresponse berfungsi ketika request berhasil dan mengembalikan data.

            override fun onFailure(call: Call<BooksResponse>, t: Throwable) {
                isLoading(false)
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_LONG).show()
                Log.d("onFailure", t.message.toString())
            }

        })
    }
    //onfailure berfungsi ketika ada kessalahan saat request.

    private fun isLoading(boolean: Boolean){
        when(boolean) {
            true -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnSearch.isEnabled = false
            }
            //Apabila aplikasi sedang dalam proses mencari maka tombol cari akan disable.
            false -> {
                binding.progressBar.visibility = View.GONE
                binding.btnSearch.isEnabled = true
            }
        }
    }
}
//jika aplikasi telas selesai memproses dan loading telah selesai maka tombol cari akan enable.