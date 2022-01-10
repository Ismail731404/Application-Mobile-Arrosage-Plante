package fr.uparis.diderot.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmeDialog : DialogFragment() {



    interface ConfirmeDialogListener {
        fun onDialogPOsitiveClick()
        fun onDialogNegativeClick()
    }

    var listener: ConfirmeDialogListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Acceptez-vous la suppression de cette plant ?")
            .setPositiveButton("oh oui !", DialogInterface.OnClickListener { _, _ ->
                listener?.onDialogPOsitiveClick()
            })
            .setNegativeButton("Euh... Non", DialogInterface.OnClickListener { _, _ ->
                listener?.onDialogNegativeClick()
            })
        return builder.create()
    }





}