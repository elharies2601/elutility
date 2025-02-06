package id.elutility.core.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding, FA: Activity> : Fragment() {
    private var _binding: VB? = null

    protected val binding: VB
        get() = _binding
            ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")

    protected val fActivity: FA
        get() = requireActivity() as FA

    protected abstract fun initAction()
    protected abstract fun initIntent()
    protected abstract fun initObserver()
    protected abstract fun initProcess()
    protected abstract fun initUI()
    protected abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initIntent()
        initProcess()
        initObserver()
        initAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}