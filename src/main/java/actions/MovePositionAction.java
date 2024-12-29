package actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class MovePositionAction extends AbstractGameAction {
    private AbstractCreature c;
    public float dX;
    public float dY;
    public float dialogX;
    public float dialogY;
    public MovePositionAction(AbstractCreature creature,float x ,float y) {
        this.c=creature;
        this.dX = x;
        this.dY = y;
        this.dialogX = ((this.dX-(float)Settings.WIDTH * 0.75F))/Settings.xScale;
        this.dialogY =170.0F * Settings.scale + (this.dY-AbstractDungeon.floorY)/Settings.yScale;

    }

    public void update() {
        c.drawX = this.dX;
        c.drawY = this.dY;
        c.dialogX = 0f;
        c.dialogY = this.dialogY;
        //BaseMod.logger.info("u dialog x"+c.dialogX);
        //BaseMod.logger.info("u dialog y"+c.dialogY);
        c.hb.move(c.drawX + c.hb_x + c.animX, c.drawY + c.hb_y + c.hb_h / 2.0F);
        c.healthHb.move(c.hb.cX, c.hb.cY - c.hb_h / 2.0F - c.healthHb.height / 2.0F);
        this.isDone = true;
    }
}