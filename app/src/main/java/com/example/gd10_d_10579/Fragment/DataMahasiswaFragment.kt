package com.example.gd10_d_10579.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gd10_d_10579.MahasiswaAdapter
import com.example.gd10_d_10579.MahasiswaData
import com.example.gd10_d_10579.RClient
import com.example.gd10_d_10579.ResponseDataMahasiswa
import com.example.gd10_d_10579.databinding.FragmentDataMahasiswaBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DataMahasiswaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("UNREACHABLE_CODE")
class DataMahasiswaFragment : Fragment() {
    private var _binding: FragmentDataMahasiswaBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val listMahasiswa = ArrayList<MahasiswaData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataMahasiswaBinding.inflate(inflater,
            container, false)
        return binding.root
        getDataMahasiswa()
    }
    override fun onStart() {
        super.onStart()
        getDataMahasiswa()
    }
    private fun getDataMahasiswa() {
        binding.rvData.setHasFixedSize(true)
        binding.rvData.layoutManager= LinearLayoutManager(context)
        val bundle = arguments
        val cari = bundle?.getString(/* key = */ "cari")

        binding.progressBar.visibility

        RClient.instances.getData(cari).enqueue(object : retrofit2.Callback<ResponseDataMahasiswa>{
            override fun onResponse(
                call: Call<ResponseDataMahasiswa>,
                response: Response<ResponseDataMahasiswa>
            ) {
                if (response.isSuccessful){
                    listMahasiswa.clear()
                    response.body()?.let {
                        listMahasiswa.addAll(it.data) }
                    val adapter =
                        MahasiswaAdapter(listMahasiswa, requireContext())
                    binding.rvData.adapter = adapter
                    adapter.notifyDataSetChanged()
                    binding.progressBar.isVisible = false
                }

            }

            override fun onFailure(call: Call<ResponseDataMahasiswa>, t: Throwable) {

            }

        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

