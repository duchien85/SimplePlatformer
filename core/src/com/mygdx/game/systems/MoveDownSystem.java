package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.MoveDownComponent;
import com.mygdx.game.components.PositionComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MoveDownSystem extends AbstractSystem {

	private MyGdxGame game;
	private float dist;
	private float distBeforeNewPlatform = Settings.PLATFORM_START_HEIGHT;
	
	public MoveDownSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);
		
		game = _game;
	}


	@Override
	public Class getEntityClass() {
		return MoveDownComponent.class;
	}

	
	@Override
	public void process() {
		dist = 20 * Gdx.graphics.getDeltaTime();
		distBeforeNewPlatform -= dist;
		
		if (distBeforeNewPlatform < 0) {
			game.lvl.generateRow(Settings.LOGICAL_HEIGHT_PIXELS);
			distBeforeNewPlatform = Settings.PLATFORM_SPACING;
		}
		
		super.process();
	}
	
	
	@Override
	public void processEntity(AbstractEntity entity) {
		MoveDownComponent gic = (MoveDownComponent)entity.getComponent(MoveDownComponent.class);
		if (gic != null) {
			PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);
			if (pos.rect.top < 0) {
				entity.remove();
				return;
			}
			pos.rect.move(0, -dist);

		}
	}

}
