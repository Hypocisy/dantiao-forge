package com.kyogi.dantiao.request;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.*;

public class RequestsHandler {

    private final Set<Request> requests = new HashSet<>();
    private Map<Request, BukkitTask> timers = new HashMap<Request, BukkitTask>();
    private final Map<Request, Integer> times = new HashMap<>();

    public Request getRequest(ServerPlayer sender, ServerPlayer receiver) {
        if (requests.isEmpty()) {
            return null;
        }
        for (Request request : requests) {
            if (request.getSender().equals(sender)
                    && request.getReceiver().equals(receiver)) {
                return request;
            }
        }
        return null;
    }

    public long getTime(ServerPlayer sender, ServerPlayer receiver) {
        if (getRequest(sender, receiver) == null) {
            return System.currentTimeMillis();
        }
        return times.get(getRequest(sender, receiver));
    }

    public void addRequest(ServerPlayer sender, ServerPlayer receiver) {
        Request request = new Request(sender, receiver);
        requests.add(request);
        times.put(request, 0);
        times.put(request, times.get(request) + 1);
        if (times.get(request) == 60) {
            if (getRequest(sender, receiver) != null) {
                removeRequest(sender, receiver);
                if (sender != null) {
                    sender.sendSystemMessage(Component.literal("&b你发送给" + receiver.getName().getString() + "的请求长时间未得处理，已取消..."));
                }
            }
//		timers.get(request).cancel();
//		timers.put(request, timer);
        }
    }

    public void removeRequest(ServerPlayer sender, ServerPlayer receiver) {
        Request request1 = getRequest(sender, receiver);
        requests.remove(request1);
        if (!timers.containsKey(request1)) {
            return;
        }
        timers.get(request1).cancel();
        timers.remove(request1);
        if (!times.containsKey(request1)) {
            return;
        }
        times.remove(request1);
    }

    public List<ServerPlayer> getReceivers(ServerPlayer sender) {
        List<ServerPlayer> list = new ArrayList<>();
        for (Request request2 : requests) {
            if (request2.getSender().equals(sender)) {
                list.add(request2.getReceiver());
            }
        }
        return list;
    }

    public List<ServerPlayer> getSenders(ServerPlayer receiver) {
        List<ServerPlayer> list = new ArrayList<>();
        for (Request request : requests) {
            if (request.getReceiver().equals(receiver)) {
                list.add(request.getSender());
            }
        }
        return list;
    }

    /*
     * cause: 0:开赛了 1:下线了
     */
    public void clearRequests(ServerPlayer player1, int cause, ServerPlayer player2) {
        for (ServerPlayer player : getSenders(player1)) {
            removeRequest(player, player1);
            if (cause == 0) {
                if (!player.equals(player2)) {
                    player1.sendSystemMessage(Component.literal("&7玩家 &f"+player.getName().getString()+" &7开始了别的比赛，之前未处理的请求已取消..."));
                }
            }
            if (cause == 1)
                player.sendSystemMessage(Component.literal("&7玩家 &f"+player1.getName().getString()+" &7暂时下线了，之前未处理的请求已取消..."));
        }
        for (ServerPlayer player : getReceivers(player1)) {
            removeRequest(player1, player);
            if (cause == 0) {
                if (!player.equals(player2)) {
                    player1.sendSystemMessage(Component.literal("&7玩家 &f"+player.getName().getString()+" &7暂时下线了，之前未处理的请求已取消..."));
                }
            }
            if (cause == 1)
                player.sendSystemMessage(Component.literal("&7玩家 &f"+player1.getName().getString()+" &7开始了别的比赛，请忽视之前的请求..."));

        }
    }
}
