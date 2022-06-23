package org.freedesktop.gstreamer.webrtc

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class PeerAdapter(
    private val trackPeers: List<TrackPeerMap>,
    private val context: Context
) : ListAdapter<TrackPeerMap, PeerViewHolder>(DIFFUTIL_CALLBACK) {

    companion object {
        val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<TrackPeerMap>() {
            override fun areItemsTheSame(
                oldItem: TrackPeerMap,
                newItem: TrackPeerMap
            ) = oldItem.peerID == newItem.peerID

            override fun areContentsTheSame(
                oldItem: TrackPeerMap,
                newItem: TrackPeerMap
            ) = oldItem.peerID == newItem.peerID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.webrtc_view, parent, false)
        return PeerViewHolder(view, ::getItem)
    }

    override fun onBindViewHolder(holder: PeerViewHolder, position: Int) {
        getItem(position)?.let {
            holder.stopSurfaceView()
            holder.bind(it, context)
        }
    }

    override fun onViewAttachedToWindow(holder: PeerViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.startSurfaceView()
    }

    override fun onViewDetachedFromWindow(holder: PeerViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopSurfaceView()
    }

}