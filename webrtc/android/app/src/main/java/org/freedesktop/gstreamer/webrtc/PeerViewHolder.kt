package org.freedesktop.gstreamer.webrtc

import android.app.Application
import android.content.Context
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import org.freedesktop.gstreamer.WebRTC

class PeerViewHolder(view: View, private val getItem: (Int) -> TrackPeerMap) :
    RecyclerView.ViewHolder(view), SurfaceHolder.Callback {

    private val TAG = PeerViewHolder::class.java.simpleName
    private lateinit var application: Application
    private lateinit var webrtcView: GStreamerSurfaceView
    private lateinit var trackPeerMap: TrackPeerMap
    private lateinit var context: Context
    private var webRTC: WebRTC? = null
    private  var wake_lock: WakeLock? = null

    init {

    }


    fun startSurfaceView() {
    }

    fun stopSurfaceView() {
    }

    fun bind(
        trackPeerMap: TrackPeerMap,
        context: Context
    ) {

        val gsv = itemView.findViewById<GStreamerSurfaceView>(R.id.remote_view)
        this.webrtcView = gsv
        this.trackPeerMap = trackPeerMap
        this.context = context

        try {
            WebRTC.init(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        wake_lock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GStreamer WebRTC")
        wake_lock?.setReferenceCounted(false)

        webRTC = WebRTC()

        webRTC?.signallingServer = trackPeerMap.url
        webRTC?.callID = trackPeerMap.peerID.toString()
        webRTC?.surface = gsv.holder.surface
        webRTC?.callOtherParty()
        wake_lock?.acquire()

        val sh: SurfaceHolder = gsv.holder
        sh.addCallback(this)

        itemView.findViewById<TextView>(R.id.peerName).text = trackPeerMap.peerID.toString()
    }

//    protected fun onDestroy() {
//        webRTC?.close()
//    }


    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {
        Log.d(
            "GStreamer", "Surface changed to format " + format + " width "
                    + width + " height " + height
        )
        webRTC?.surface = holder.surface
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d("GStreamer", "Surface destroyed")
        webRTC?.surface = null
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("GStreamer", "Surface created: " + holder.surface)
    }

}