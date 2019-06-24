package example.com.excerciseproject.view.order

import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import de.cketti.mailto.EmailIntentBuilder
import example.com.excerciseproject.R
import example.com.excerciseproject.presenter.ProcessOrderPresenter
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_process_order.*

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class ProcessOrderActivity : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener,
    ProcessOrderView
{

    private var mapFragment: MapFragment? = null
    private var googleMap: GoogleMap? = null
    private var googleApiClient: GoogleApiClient? = null
    private var mapView: View? = null
    private var simpleLocation: SimpleLocation? = null

    private val presenter = ProcessOrderPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_order)
        presenter.onAttach(this)

        mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapView = mapFragment!!.view
        mapFragment!!.getMapAsync(this)

        simpleLocation = SimpleLocation(this)
        simpleLocation!!.setListener {
            simpleLocation?.position?.let { position ->
                presenter.onClickMap(LatLng(position.latitude, position.longitude))
                simpleLocation!!.endUpdates()
            }
        }
        simpleLocation!!.beginUpdates()

        button_send.setOnClickListener {
            val name = input_name.text.toString()
            val phone = input_phone.text.toString()

            presenter.onClickSend(
                name,
                phone,
                checkbox_send_coordinates.isChecked
            )
        }

        input_phone.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onChangePhone(s.toString())
            }
        })
    }

    override fun showButtonEnabled(enabled: Boolean) {
        button_send.isEnabled = enabled
        if (enabled) {
            button_send.setBackgroundColor(resources.getColor(R.color.colorAccent))
        } else {
            button_send.setBackgroundDrawable(null)
        }
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showCoordinate(coordinate: LatLng) {
        googleMap?.clear()
        googleMap?.addMarker(MarkerOptions().position(coordinate))
    }

    override fun openEmailApp(title: String, body: String, email: String) {
        EmailIntentBuilder.from(this)
            .to(email)
            .subject(title)
            .body(body)
            .start()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.setOnMapClickListener {
            presenter.onClickMap(it)
        }

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = this.googleMap!!
                .setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))

            if (!success) {
                Log.e("TAG", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("TAG", "Can't find style. Error: ", e)
        }


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then over   riding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        //This line will show your current location on Map with GPS dot
        this.googleMap!!.isMyLocationEnabled = true
        locationButton()
    }

    private fun locationButton() {
        val mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment

        val locationButton = (mapFragment.view!!.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(Integer.parseInt("2"))
        if (locationButton != null && locationButton.layoutParams is RelativeLayout.LayoutParams) {
            // location button is inside of RelativeLayout
            val params = locationButton.layoutParams as RelativeLayout.LayoutParams

            // Align it to - parent BOTTOM|LEFT
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)

            // Update margins, set to 10dp
            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150f,
                resources.displayMetrics).toInt()
            params.setMargins(margin, margin, margin, margin)

            locationButton.layoutParams = params
        }
    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onLocationChanged(p0: Location?) {

    }


}