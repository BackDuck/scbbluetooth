package com.example.scbbluetooth.presentation.ui.error

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import com.example.scbbluetooth.R


class ErrorDialogFragment : DialogFragment() {

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        return builder
            .setView(R.layout.fragment_error)
            .setOnKeyListener { _, keyCode, _ ->
                keyCode == KeyEvent.KEYCODE_BACK
            }
            .setCancelable(false)
            .create()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

}