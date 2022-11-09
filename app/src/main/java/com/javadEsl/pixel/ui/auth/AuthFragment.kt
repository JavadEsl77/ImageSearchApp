package com.javadEsl.pixel.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.javadEsl.pixel.R
import com.javadEsl.pixel.databinding.FragmentAuthBinding
import com.javadEsl.pixel.databinding.LayoutBottomSheetOptBinding
import com.javadEsl.pixel.helper.extensions.fadeIn
import com.javadEsl.pixel.helper.extensions.fadeOut
import com.javadEsl.pixel.helper.extensions.hide
import com.javadEsl.pixel.helper.extensions.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    private var phone = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentAuthBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        binding.apply {
            buttonOpt.setOnClickListener {
                viewModel.startTimer()
                val phoneNumber = edittextPhoneNumber.text.toString()
                if (phone == "") {
                    if (phoneNumber != "" && phoneNumber.length > 10) {
                        showOptBottomSheetDialog(phoneNumber)
                        phone = phoneNumber
                        edittextPhoneNumber.clearFocus()
                    } else {
                        if (phoneNumber == "")
                            edittextPhoneNumber.error = "شماره موبایل را وراد کنید !"
                        else
                            edittextPhoneNumber.error =
                                "شماره موبایل نمی تواند کمتر از  11 رقم باشد !"
                    }
                } else {
                    if (phone != phoneNumber) {
                        textViewAlertPhone.fadeIn()
                    } else {
                        textViewAlertPhone.fadeOut()
                        showOptBottomSheetDialog(phoneNumber)
                    }
                }


                viewModel.startTime.observe(viewLifecycleOwner) {
                    if (AuthViewModel.FINISHED == it) {
                        phone = ""
                        buttonOpt.text = "دریافت کد"
                        textViewAlertPhone.fadeOut()
                    } else {
                        buttonOpt.text = "وارد کردن کد تایید"
                        textViewAlertPhone.text =
                            " از آنجا که شماره جدیدی برای ارسال کد وارد کردید باید به مدت  $it  صبر نمایید "
                    }
                }
            }
        }
    }

    private fun observe() {

    }

    private fun showOptBottomSheetDialog(phoneNumber: String) {
        val bottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val sheetDialog = LayoutBottomSheetOptBinding.inflate(LayoutInflater.from(requireContext()))
        bottomSheetDialog.setContentView(sheetDialog.root)

        sheetDialog.apply {

            textViewAlertInfoNumber.text = " کد تایید به شماره تلفن  $phoneNumber  ارسال شد "

            viewModel.startTime.observe(viewLifecycleOwner) {
                if (AuthViewModel.FINISHED == it) {
                    textViewOptTimer.hide()
                    layoutRetrySend.show()
                } else {
                    textViewOptTimer.show()
                    layoutRetrySend.hide()
                    textViewOptTimer.text = it
                }
            }

            textViewRetrySend.setOnClickListener {
                viewModel.startTimer()
            }
        }

        bottomSheetDialog.show()
    }
}



