package monsters.act1.boss;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.RedFireballEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import monsters.act4.AnnihilateImmortalSword;
import monsters.act4.ExterminateImmortalSword;
import monsters.act4.SlayingImmortalSword;
import monsters.act4.TrapImmortalSword;
import vfx.ColorfulThrowDaggerEffect;

import java.util.Iterator;

public class BullDemonKing extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:BullDemonKing";
    public static final String FROM1 = "img/monsters/bull_demon_king_1.png";
    public static final String FROM2 = "img/monsters/bull_demon_king_2.png";
    public static final String FROM3 = "img/monsters/bull_demon_king_3_h.png";
    public static final String FROM4 = "img/monsters/bull_demon_king_3.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:BullDemonKing");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;


    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 50;
    private static final int HP_MAX = 55;
    private static final int A_HP_MIN = 57;
    private static final int A_HP_MAX = 62;
    private static final int DMG1 = 12;
    private static final int DMG2 = 8;
    private static final int DMG3 = 3;
    private static final int HITS = 5;
    private static final int A_HITS = 6;
    private static final int A_DMG1 = 14;
    private static final int A_DMG2  = 10;
    private static final int A_DMG3  = 3;
    private static final int BLOCK1 = 16;
    private static final int A_BLOCK1 = 18;
    private static final int BLOCK2 = 7;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 2;
    private static final int A_MGC1 = 3;
    private static final int MGC2 = 5;
    private static final int A_MGC2 = 7;
    private boolean recover = false;
    private int Dmg1;
    private int Dmg2;
    private int Dmg3;
    private int HitTime;
    private int magicAmt1;
    private int magicAmt2;
    private int b1;
    private int b2;
    public int turn_count = 0 ;
    private int egg_turn = 0 ;
    private int from ;
    private boolean has_hand;


    public BullDemonKing() {
        this(0.0F, 0.0F);
    }
    public BullDemonKing(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 280.0F, 300.0F, FROM1,x,y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(A_HP_MIN, A_HP_MAX);
        } else {
            setHp(HP_MIN, HP_MAX);

        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.Dmg1 = A_DMG1;
            this.Dmg2 = A_DMG2;
            this.Dmg3 = A_DMG3;
            this.b1 =A_BLOCK1;
            this.b2 =A_BLOCK2;
        } else {
            this.Dmg1 = DMG1;
            this.Dmg2 = DMG2;
            this.Dmg3 = DMG3;
            this.b1 =BLOCK1;
            this.b2 =BLOCK2;

        }
        if (AbstractDungeon.ascensionLevel >= 19) {
            this.magicAmt1 = A_MGC1;
            this.magicAmt2 = A_MGC2;
            this.HitTime=A_HITS;
        } else {
            this.magicAmt1 = MGC1;
            this.magicAmt2 = MGC2;
            this.HitTime=HITS;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));
        this.damage.add(new DamageInfo(this, this.Dmg3));
        this.damage.add(new DamageInfo(this, this.b1));
        this.damage.add(new DamageInfo(this, this.b2));
        this.recover = false;
        this.from = 1;

    }

    public void usePreBattleAction() {
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");
        AbstractDungeon.getCurrRoom().cannotLose = true;

    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch(this.nextMove) {
            case 1:
                //from1,2 attack
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                //from2 attack_debuff
                this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                this.addToBot(new ApplyPowerAction(p, this, new VulnerablePower(p, 1, true), 1));
                break;
            case 3:
                //from2 attack_strength
                int i = 0;
                while(true) {
                    if (i >= this.HitTime) {
                        break label;
                    }
                    this.addToBot(new VampireDamageAction(p,this.damage.get(2), AbstractGameAction.AttackEffect.FIRE));
                    i++;
                }
            case 4:
                //from3 wst
                if (this.from == 3){
                    if (!this.hasPower("Intangible")) {
                        this.addToBot((new ChangeStateAction(this, "WST")));
                        this.addToBot(new ApplyPowerAction(this, this, new IntangiblePower(this, 1)));
                        this.img = ImageMaster.loadImage(FROM4);
                    }
                }
                break;
            case 5:
                //from3 attack
                this.addToBot((new ChangeStateAction(this, "ATTACK")));
                this.addToBot(new VFXAction(p, new ScreenOnFireEffect(), 0.5F));
                this.addToBot(new WaitAction(0.5f));
                this.addToBot(new DamageAction(p, this.damage.get(3), AbstractGameAction.AttackEffect.FIRE));
                break ;
            case 6:
                this.img  = ImageMaster.loadImage(FROM3);
                this.addToBot(new ApplyPowerAction(p, this, new FrailPower(p, this.magicAmt1, true), this.magicAmt1));
                this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.magicAmt1), this.magicAmt1));
                break ;
            case 7:
                //??? from3
                this.has_hand =false;
                Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
                while (true){
                    if (!var1.hasNext()) {
                        break;
                    }
                    AbstractMonster mo = (AbstractMonster)var1.next();
                    if (mo instanceof BullDemonKingHand && !mo.isDying) {
                        this.has_hand =true;
                    }
                }
                this.img  = ImageMaster.loadImage(FROM3);
                this.addToBot((new ChangeStateAction(this, "ATTACK")));
                if (!this.has_hand){
                    this.addToBot(new SpawnMonsterAction(new BullDemonKingHand(-900.0F, 375.0F), true));
                    this.addToBot(new SpawnMonsterAction(new BullDemonKingHand(-50.0F, 375.0F), true));
                }
                break ;
            case 8:
                //??? from1 to 2

                this.maxHealth = AbstractDungeon.monsterHpRng.random(this.maxHealth+25, this.maxHealth+30);
                this.addToBot(new HealAction(this, this, this.maxHealth));
                this.from = 2;
                this.img  = ImageMaster.loadImage(FROM2);
                this.halfDead = false;
                break ;
            case 9:
                //??? from2 to 3

                this.maxHealth = AbstractDungeon.monsterHpRng.random(this.maxHealth+45, this.maxHealth+50);
                this.addToBot(new HealAction(this, this, this.maxHealth));
                this.from = 3;
                this.img  = ImageMaster.loadImage(FROM3);
                ((AbstractPlayer) p).movePosition((float) Settings.WIDTH / 2.0F, 280.0F * Settings.yScale);
                movePosition((float) Settings.WIDTH / 2.0F, 600.0F * Settings.yScale);
                this.hb_w = 700.f;
                this.addToBot(new SpawnMonsterAction(new BullDemonKingHand(-900.0F, 375.0F), true));
                this.addToBot(new SpawnMonsterAction(new BullDemonKingHand(-50.0F, 375.0F), true));
                AbstractDungeon.getCurrRoom().cannotLose = false;
                this.halfDead = false;
                break ;


        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (turn_count==1){
            this.addToBot((new TalkAction(this,DIALOG[0], 2.5F, 2.5F)));
        }
        int a = AbstractDungeon.aiRng.random(1,100);
        if (this.from == 1){
            //1……
            this.setMove(MOVES[1], (byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
        } else if (this.from == 2) {
            //1,2,3 .flow:
            if (AbstractDungeon.ascensionLevel >= 19) {
                if (a<40) {
                    this.setMove(MOVES[2], (byte) 2, Intent.ATTACK_DEBUFF, (this.damage.get(1)).base);
                } else if (a<80){
                    this.setMove(MOVES[3], (byte) 3, Intent.ATTACK_BUFF, (this.damage.get(2)).base, this.HitTime, true);
                }else {
                    this.setMove(MOVES[1], (byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
                }
            }
            if (a < 40 && !lastTwoMoves((byte) 1)) {
                this.setMove(MOVES[1], (byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
            } else if (a < 80 && !lastTwoMoves((byte) 2)) {
                this.setMove(MOVES[2], (byte) 2, Intent.ATTACK_DEBUFF, (this.damage.get(1)).base);
            } else if (a < 120 && !lastTwoMoves((byte) 3)) {
                this.setMove(MOVES[3], (byte) 3, Intent.ATTACK_BUFF, (this.damage.get(2)).base, this.HitTime, true);
            } else {
                this.setMove(MOVES[1], (byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
            }
        }else {
            //4，6，5，7 4wst 6debuff 5att 7???
            if (AbstractDungeon.ascensionLevel >= 19){
                if (lastMoveBefore((byte) 4) || lastMove((byte) 7)) {
                    this.setMove(MOVES[4], (byte) 4, Intent.BUFF);
                }else if (lastTwoMoves((byte) 5)){
                    this.setMove(MOVES[7], (byte) 7, Intent.UNKNOWN);
                }else if (lastMove((byte) 6) || lastMove((byte) 5)){
                    this.setMove(MOVES[5], (byte) 5, Intent.ATTACK, (this.damage.get(3)).base);
                }else if (lastMove((byte) 4)){
                    this.setMove(MOVES[6], (byte) 6, Intent.DEBUFF);
                }
                else {
                    this.setMove(MOVES[4], (byte) 4, Intent.BUFF);
                }
            }else {
                if (lastMove((byte) 6)){
                    this.setMove(MOVES[4], (byte) 4, Intent.BUFF);
                }else if (lastMove((byte) 4)){
                    this.setMove(MOVES[5], (byte) 5, Intent.ATTACK, (this.damage.get(3)).base);
                }else if (lastMove((byte) 5)){
                    this.setMove(MOVES[7], (byte) 7, Intent.UNKNOWN);
                }else {
                    this.setMove(MOVES[6], (byte) 6, Intent.DEBUFF);
                }
            }
        }
    }

    public void changeState(String stateName) {
        byte var3 = -1;
        if (stateName.equals("WST")) {
            var3 = 1;
        }
        else if (stateName.equals("ATTACK")) {
            var3 = 0;
        }

        switch(var3) {
            case 0:
                this.img  = ImageMaster.loadImage(FROM3);
                break;
            case 1:
                this.img  = ImageMaster.loadImage(FROM4);
                break;
        }
    }

    private void playDeathSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_SLAVERLEADER_2A");
        } else {
            CardCrawlGame.sound.play("VO_SLAVERLEADER_2B");
        }

    }

    private void movePosition(float x, float y){
        this.drawX = x;
        this.drawY = y;
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;
        this.refreshHitboxLocation();
    }

    @Override
    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            this.playDeathSfx();
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            this.onBossVictoryLogic();
            Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                if (!m.isDead && !m.isDying) {
                    AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                    AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
                }
            }
        }
    }

    public void damage (DamageInfo info){
        super.damage(info);
        if (this.currentHealth <= 0 && AbstractDungeon.getCurrRoom().cannotLose) {
            this.halfDead = true;
            Iterator s = this.powers.iterator();

            AbstractPower p;
            while (s.hasNext()) {
                p = (AbstractPower) s.next();
                p.onDeath();
            }

            s = AbstractDungeon.player.relics.iterator();

            while (s.hasNext()) {
                AbstractRelic r = (AbstractRelic) s.next();
                r.onMonsterDeath(this);
            }

            this.addToTop(new ClearCardQueueAction());
            s = this.powers.iterator();

            while (true) {
                do {
                    if (!s.hasNext()) {
                        if (this.from == 1) {
                            this.setMove((byte) 8, Intent.UNKNOWN);
                            this.createIntent();
                            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[1]));
                            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 8, Intent.UNKNOWN));
                            this.applyPowers();
                            return;
                        } else if (this.from == 2) {
                            AbstractDungeon.getCurrRoom().cannotLose = false;
                            this.setMove((byte) 9, Intent.UNKNOWN);
                            this.createIntent();
                            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[2]));
                            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 9, Intent.UNKNOWN));
                            this.applyPowers();
                            return;
                        }
                    }

                    p = (AbstractPower) s.next();
                } while (p.type != AbstractPower.PowerType.DEBUFF);

                s.remove();
            }
        }
    }


}