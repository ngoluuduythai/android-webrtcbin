/* GStreamer
 *
 * Copyright (C) 2014 Sebastian Dröge <sebastian@centricular.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301, USA.
 */
package org.freedesktop.gstreamer.webrtc

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WebRTC : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val trackPeerMaps: MutableList<TrackPeerMap> = mutableListOf()

        for (i in 1..10) {
            trackPeerMaps.add(TrackPeerMap(i, "wss://webrtc.nirbheek.in:8443"))
        }

        val peerAdapter = PeerAdapter(trackPeerMaps, this)
        peerAdapter.submitList(trackPeerMaps)


        recyclerView.apply {
            layoutManager = GridLayoutManager(this@WebRTC, 2)
            adapter = peerAdapter
        }
    }
}