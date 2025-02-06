package id.elharies.elutility

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import id.elharies.elutility.databinding.ActivityNextBinding
import id.elharies.elutility.model.TempModel
import id.elutility.core.base.BaseActivity
import id.elutility.core.ext.clearComma
import id.elutility.core.ext.getSerializable
import id.elutility.core.ext.textWatcherFormatNumber
import id.elutility.core.ext.toFormat
import id.elutility.core.ext.toNumberOnly

class NextActivity : BaseActivity<ActivityNextBinding>() {

    private var nextTempMdl: TempModel? = null

    companion object {
        const val KEY_TEMP = "keyTemp"
        const val KEY_TEMP_RESULT = "keyTempResult"

        fun startIntent(context: Context, data: TempModel): Intent {
            val intent = Intent(context, NextActivity::class.java).apply {
                putExtra(KEY_TEMP, data)
            }
            return intent
        }
    }

    override fun getBinding(inflater: LayoutInflater): ActivityNextBinding {
        return ActivityNextBinding.inflate(inflater)
    }

    override fun initAction() {
        binding.btnSendBack.setOnClickListener {
            val intent = Intent()
            intent.putExtra(
                KEY_TEMP_RESULT,
                nextTempMdl?.copy(
                    number = binding.etNumber.text.toString().toNumberOnly().clearComma().toDouble()
                )
            )
            setResult(RESULT_OK, intent)
            finishActivity()
        }
    }

    override fun initIntent() {
        nextTempMdl = intent.getSerializable(KEY_TEMP)
    }

    override fun initObserver() {

    }

    override fun initProcess() {

    }

    override fun initUI() {
        nextTempMdl?.let {
            binding.tvTanggal.text = it.date
            binding.etNumber.setText(it.number.toFormat())
        }

        binding.etNumber.textWatcherFormatNumber {

        }
    }

}