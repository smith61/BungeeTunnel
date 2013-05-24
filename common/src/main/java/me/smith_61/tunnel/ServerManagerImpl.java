package me.smith_61.tunnel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

import me.smith_61.tunnel.Server;
import me.smith_61.tunnel.ServerManager;
import me.smith_61.tunnel.TunnelDataListener;
import me.smith_61.tunnel.TunnelListener;
import me.smith_61.tunnel.exceptions.ChannelRegisteredException;


public class ServerManagerImpl implements ServerManager {

	private final Map<String, TunnelDataListener> listeners;
	
	public ServerManagerImpl() {
		this.listeners = new HashMap<String, TunnelDataListener>();
	}
	
	@Override
	public Server[] getServers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server getServer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerTunnelDataListener(String channel, TunnelDataListener listener) throws ChannelRegisteredException {
		Preconditions.checkNotNull(channel, "Channel");
		Preconditions.checkNotNull(listener, "Listener");
		
		channel = channel.trim().toLowerCase();
		synchronized(this.listeners) {
			if(this.listeners.containsKey(channel)) {
				throw new ChannelRegisteredException(channel);
			}
			else {
				this.listeners.put(channel, listener);
			}
		}
	}

	@Override
	public void unregisterTunnelDataListener(String channel) {
		channel = channel.trim().toLowerCase();
		synchronized(this.listeners) {
			this.listeners.remove(channel);
		}
	}

	@Override
	public void registerTunnelListener(TunnelListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterTunnelListener(TunnelListener listener) {
		// TODO Auto-generated method stub

	}

	
	public TunnelDataListener getListener(String channel) {
		channel = channel.trim().toLowerCase();
		synchronized(this.listeners) {
			return this.listeners.get(channel);
		}
	}

	@Override
	public Server getThisServer() {
		// TODO Auto-generated method stub
		return null;
	}
}
