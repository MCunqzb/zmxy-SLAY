package actions;

import basemod.BaseMod;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class ControlPhantomAction extends AbstractGameAction {
    public ControlPhantomAction(int amount) {
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.amount=amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            Iterator var1 = AbstractDungeon.player.hand.group.iterator();
            int i = 0;
            while(var1.hasNext() && i < amount) {
                AbstractCard c = (AbstractCard)var1.next();
                Iterator var3 = AbstractDungeon.actionManager.cardQueue.iterator();
                i++;
                while(var3.hasNext()) {
                    CardQueueItem q = (CardQueueItem)var3.next();
                    if (q.card == c) {
                    }
                }

                c.freeToPlayOnce = true;
                switch(c.target) {
                    case SELF_AND_ENEMY:
                    case ENEMY:
                        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c, AbstractDungeon.getRandomMonster()));
                        break;
                    case SELF:
                    case ALL:
                    case ALL_ENEMY:
                    case NONE:
                    default:
                        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c, (AbstractMonster)null));
                }
            }
        }

        this.tickDuration();
    }
}
