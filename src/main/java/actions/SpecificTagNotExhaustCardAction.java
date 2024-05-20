package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class SpecificTagNotExhaustCardAction extends AbstractGameAction {
    private AbstractCard targetCard;
    private CardGroup group;
    private float startingDuration;
    public AbstractCard.CardTags Tag;
    public SpecificTagNotExhaustCardAction(AbstractCard.CardTags tag, CardGroup group, boolean isFast) {
        this.Tag = tag;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.EXHAUST;
        this.group = group;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public SpecificTagNotExhaustCardAction(AbstractCard.CardTags tag, CardGroup group) {
        this(tag, group, false);
    }

    public void update() {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList();
        Iterator var2 = this.group.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (!c.hasTag(this.Tag)) {
                cardsToExhaust.add(c);
            }
        }
        var2 = cardsToExhaust.iterator();
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            this.addToTop(new ExhaustSpecificCardAction(c, this.group));
        }

        this.isDone = true;


    }
}
