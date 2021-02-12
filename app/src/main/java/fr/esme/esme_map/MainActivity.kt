package fr.esme.esme_map

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_ENABLE_BT:Int = 1;
    private val TAG = MainActivity::class.qualifiedName;
    lateinit var bluetoothAdapter: BluetoothAdapter

    @SuppressLint("MissingPermission")-
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if ( bluetoothAdapter == null ) {
            Toast.makeText(
                this, "Bluetooth not supported on this device",
                Toast.LENGTH_LONG).show();
            return;
        }

        // Si le bluetooth n'est pas activé, on propose de l'activer
        if ( ! bluetoothAdapter.isEnabled() ) {
            // Demande à activer l'interface bluetooth
            var intent =  Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_CODE_ENABLE_BT);

            val pairedDevices: Set<BluetoothDevice> = bluetoothAdapter.getBondedDevices()
            if (pairedDevices.size > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (device in pairedDevices) {
                    val deviceName: String = device.getName()
                    val deviceHardwareAddress: String = device.getAddress() // MAC address
                    Log.e(TAG, "Device name:" + deviceName + "Device Hardware/MAC Address:" + deviceHardwareAddress);
                }
            }

            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }

            bluetoothAdapter.startDiscovery();
            
            }
        }
}