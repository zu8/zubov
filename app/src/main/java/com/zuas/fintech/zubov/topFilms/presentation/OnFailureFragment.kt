package com.zuas.fintech.zubov.topFilms.presentation

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zuas.fintech.zubov.databinding.FragmentOnFailureBinding


class OnFailureFragment : Fragment() {

    private var _binding: FragmentOnFailureBinding? = null
    private val binding: FragmentOnFailureBinding
        get() = _binding ?: throw RuntimeException("FragmentOnFailureBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnFailureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            requireActivity().finish()
            val intent = requireActivity().intent
            requireActivity().startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): OnFailureFragment {
            return OnFailureFragment()
        }
    }
}