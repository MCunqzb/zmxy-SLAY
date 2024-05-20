package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import demoMod.MonkeyKingMod;

import java.util.ArrayList;
import java.util.Iterator;

public class AllTagToHandAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int costTarget;
    public AbstractCard.CardTags Tag;
    public AllTagToHandAction(AbstractCard.CardTags tag) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.Tag = tag;
    }

    public void update() {
        if (this.p.discardPile.size() > 0) {
            Iterator var1 = this.p.discardPile.group.iterator();

            label21:
            while(true) {
                AbstractCard card;
                do {
                    if (!var1.hasNext()) {
                        break label21;
                    }

                    card = (AbstractCard)var1.next();
                } while(!card.hasTag(this.Tag));

                this.addToBot(new DiscardToHandAction(card));
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
