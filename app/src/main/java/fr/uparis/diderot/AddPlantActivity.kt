package fr.uparis.diderot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.github.dhaval2404.imagepicker.ImagePicker
import fr.uparis.diderot.data.entity.Watering_Plant
import fr.uparis.diderot.databinding.ActivityAddPlantBinding
import java.io.File
import java.util.*
import android.content.ContentResolver
import java.security.AccessControlContext
import java.security.AccessController.getContext


class AddPlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPlantBinding
    private lateinit var uriImagedefaut: Uri
    private var localUri: Uri? = null
    private var uriImage: Uri? = null
    private var context: Context = this

    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory((application as AppApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Image par defaut
        uriImagedefaut = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.plant22))
            .appendPath(resources.getResourceTypeName(R.drawable.plant22))
            .appendPath(resources.getResourceEntryName(R.drawable.plant22))
            .build()
        //Initialement uriImage prend l'image par defaut
        uriImage = uriImagedefaut

        binding.floatingActionButton.setOnClickListener(View.OnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .cropSquare()//Crop square image, its same as crop(1f, 1f)
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        })

        binding.submit.setOnClickListener {
            //Saving Image in Internal storage
            uriImage?.let { it1 -> callback(it1) }

            binding.apply {
                if (
                    (NomCommun.text.toString().isNotEmpty() || NomLatin.text.toString()
                        .isNotEmpty()) &&
                    (NumberTimes.text.toString().isNotEmpty() && PeriodNumberTimes.text.toString()
                        .isNotEmpty())
                ) {


                    try {
                        //Calcule Next Watering Day
                        var nextwateringdate: Calendar = Calendar.getInstance()
                        nextwateringdate.set(
                            Calendar.DAY_OF_MONTH,
                            nextwateringdate.get(
                                Calendar.DAY_OF_MONTH + ((PeriodNumberTimes.text.toString()
                                    .toInt() / NumberTimes.text.toString().toInt()).toInt())
                            )
                        )


                        viewModel.insertPlantWatering(
                            Watering_Plant(
                                nom_commun = NomCommun.text.toString(),
                                nom_latin = NomLatin.text.toString(),
                                number_Times = NumberTimes.text.toString().toInt(),
                                period_Number_Times = PeriodNumberTimes.text.toString().toInt(),
                                localUri = uriImage,
                                last_Watering = Calendar.getInstance().time,
                                next_Watering = nextwateringdate.time
                            )
                        )

                        Toast.makeText(context,"Enregistrement Reusie",Toast.LENGTH_LONG).show()
                        VideContenuEditext()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            context,
                            "Merci de respecte le format du champs",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else Toast.makeText(context, "Merci de saisir tout le champs", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun VideContenuEditext() {
        binding.apply {
            coverImage.setImageURI(uriImagedefaut)
            NomCommun.setText("")
            NomLatin.setText("")
            NumberTimes.setText("")
            PeriodNumberTimes.setText("")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            // Use Uri object instead of File to avoid storage permissions
            uriImage = uri
            binding.coverImage.setImageURI(uriImage)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    fun callback(uri: Uri) {
        /* inputStream avec l’image de la plante */
        val inputStream = contentResolver.openInputStream(uri)
        /* fabriquer le nom de fichier local pour stocker l’image */
        val fileNamePrefix = "plante"
        val preferences = getSharedPreferences("numImage", Context.MODE_PRIVATE)
        val numImage = preferences.getInt("numImage", 1)
        val fileName = "$fileNamePrefix$numImage"
        /* ouvrir outputStream vers un fichier local */
        val file = File(this.filesDir, fileName)
        val outputStream = file.outputStream()
        /* sauvegarder le nouveau compteur d’image */
        preferences.edit().putInt("numImage", numImage + 1).commit()
        /* copier inputStream qui pointe sur l’image de la galerie
            * vers le fichier local */
        inputStream?.copyTo(outputStream)
        /* mémoriser Uri de fichier local dans la propriété localUri */
        localUri = file.toUri()
        outputStream.close()
        inputStream?.close()
        //éventuellement afficher l’image dans ImageView

    }


}