package actions;

import com.badlogic.gdx.utils.Predicate;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class PhantomDestructionAction extends AbstractGameAction {
    private int attackPerCard;
    private DamageInfo info;
    private float startingDuration;

    public PhantomDestructionAction(AbstractCreature target, DamageInfo info ) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList();
        Iterator var2 = AbstractDungeon.player.hand.group.iterator();
        int count = 0;
        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.rarity == AbstractCard.CardRarity.SPECIAL || c.type == AbstractCard.CardType.STATUS || c.type == AbstractCard.CardType.CURSE) {
                cardsToExhaust.add(c);
                count=count+1;
            }
        }

        int i;
        for(i = 0; i < count; ++i) {
            this.addToTop(new DamageAction(this.target, this.info, AttackEffect.FIRE));
        }

        var2 = cardsToExhaust.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        }

        this.isDone = true;


    }
}

