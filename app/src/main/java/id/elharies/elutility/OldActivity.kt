package id.elharies.elutility

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.elharies.elutility.databinding.ActivityOldBinding
import id.elharies.elutility.model.TempModel
import id.elutility.core.base.BaseActivity
import id.elutility.core.ext.clearComma
import id.elutility.core.ext.getCurrentTime
import id.elutility.core.ext.getSerializable
import id.elutility.core.ext.textWatcherFormatNumber
import id.elutility.core.ext.toNumberOnly
import id.elutility.core.ext.toString

class OldActivity : BaseActivity<ActivityOldBinding>() {

    private var amount = 0.0

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val code = it.resultCode
        if (code == RESULT_OK) {
            val data = it.data?.getSerializable<TempModel>(NextActivity.KEY_TEMP_RESULT)
            data?.let { res ->
                binding.tvResult.text = "Result: ${res.number}"
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, OldActivity::class.java))
        }
    }

    override fun getBinding(inflater: LayoutInflater): ActivityOldBinding {
        return ActivityOldBinding.inflate(inflater)
    }

    override fun initAction() {
        binding.btnSend.setOnClickListener {
            val intent = NextActivity.startIntent(
                context = this,
                data = TempModel(number = amount)
            )
            launcher.launch(intent)
        }

        binding.etAmount.textWatcherFormatNumber(onChange = {
            amount = it.toNumberOnly().clearComma().toDouble()
        })

        binding.btnShowLoading.setOnClickListener {
            showLoading()
            Handler(Looper.getMainLooper()).postDelayed({
                dismissLoading()
            }, 2000L)
        }

        binding.btnHideLoading.setOnClickListener {
            dismissLoading()
        }
    }

    override fun initIntent() {

    }

    override fun initObserver() {

    }

    override fun initProcess() {

    }

    @SuppressLint("SetTextI18n")
    override fun initUI() {
        binding.tvTanggal.text = "Tanggal: ${getCurrentTime().toString("dd MMMM yyyy")}"
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge()
//        super.onCreate(savedInstanceState)
//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
//            insets
//        }
//    }
}