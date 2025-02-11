package id.elutility.core.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.elutility.core.R

abstract class BaseActivity<VB : androidx.viewbinding.ViewBinding>: AppCompatActivity() {

    protected lateinit var binding: VB

    private var dialog: AlertDialog? = null

    protected abstract fun initAction()
    protected abstract fun initIntent()
    protected abstract fun initObserver()
    protected abstract fun initProcess()
    protected abstract fun initUI()
    protected abstract fun getBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        binding = getBinding(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        initIntent()
        initProcess()
        initObserver()
        initUI()
        initAction()
    }

    protected fun finishActivity(){
        finish()
    }

    private fun getIntent(ctx: Context, cls: Class<*>): Intent {
        return Intent(ctx, cls)
    }

    protected fun showActivity(intent: Intent){
        startActivity(intent)
    }

    protected fun showActivity(intent: Intent, requestCode:Int){
        startActivityForResult(intent, requestCode)
    }

    protected fun showActivityForResult(intent: Intent, launcher: ActivityResultLauncher<Intent>) {
        launcher.launch(intent)
    }

    protected fun showActivity(packageContext: Context, cls: Class<*>){
        val intent = getIntent(packageContext, cls)
        showActivity(intent)
    }

    protected fun showActivityFinish(ctx: Context, cls: Class<*>){
        showActivity(ctx, cls)
        finish()
    }

    protected fun showActivityFinish(intent: Intent){
        showActivity(intent)
        finish()
    }

    protected fun configureToolbar(
        withBackArrow: Boolean,
        toolbar: Toolbar?,
        title: String,
        listener: View.OnClickListener?
    ) {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            val supportActionBar = supportActionBar
                ?: throw RuntimeException("Unable to set support action bar")

            if (withBackArrow) {
                toolbar.setNavigationOnClickListener(listener)
                supportActionBar.setDisplayHomeAsUpEnabled(true)
                supportActionBar.setDisplayShowHomeEnabled(true)
                supportActionBar.title = title
            }
            supportActionBar.setDisplayShowTitleEnabled(true)
            supportActionBar.setHomeButtonEnabled(true)
        }
    }

    protected fun showLoading() {
        dialog = AlertDialog.Builder(this)
            .setView(layoutInflater.inflate(R.layout.dialog_loading, null))
            .setCancelable(false)
            .create()

        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.CENTER)
            val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 60)
            setBackgroundDrawable(inset)
        }

        dialog?.show()
    }

    protected fun dismissLoading() {
        if (dialog != null && dialog!!.isShowing) {
            dialog?.dismiss()
        }
    }
}