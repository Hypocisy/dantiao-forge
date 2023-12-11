package com.kyogi.dantiao.arenas;

import java.util.ArrayList;
import java.util.List;

public class ArenaCreatorHandler {
	private final List<ArenaCreator> arenaCreators = new ArrayList<>();
	private final List<String> creators = new ArrayList<>();

	public void addAC(String creatorName) {
		arenaCreators.add(new ArenaCreator(creatorName));
		creators.add(creatorName);
	}

	public void removeAC(String creatorName) {
		arenaCreators.remove(getArenaCreatorByName(creatorName));
		creators.remove(creatorName);
	}

	public ArenaCreator getArenaCreatorByName(String creatorName) {
		if (arenaCreators.isEmpty()) {
			return null;
		}
		for (ArenaCreator ac : arenaCreators) {
			if (ac.getCreator().equalsIgnoreCase(creatorName)) {
				return ac;
			}
		}
		return null;
	}

	public List<ArenaCreator> getArenaCreators() {
		return arenaCreators;
	}

	public List<String> getCreators() {
		return creators;
	}
}
