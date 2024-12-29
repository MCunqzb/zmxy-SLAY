package monsters.act2;

import vfx.QuietSpecialSmokeBombEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MirageJudge extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:MirageJudge";
    public static final String FROM1 = "img/monsters/judge.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:Judge");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String CHARM1 = "img/vfxs/charm1.png";
    public static final String CHARM2 = "img/vfxs/charm2.png";
    public static final String WATER0 = "img/vfxs/water_vfx_0.png";
    public static final String WATER1 = "img/vfxs/water_vfx_1.png";
    public static final String WATER2 = "img/vfxs/water_vfx_2.png";
    public static final String WATER3 = "img/vfxs/water_vfx_3.png";
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 1;
    private static final int HP_MAX = 1;
    private static final int A_HP_MIN = 1;
    private static final int A_HP_MAX = 1;
    private static final int DMG1 = 10;
    private static final int A_DMG1 = 12;
    private static final int DMG2 = 13;
    private static final int A_DMG2  = 14;
    private static final int DMG3 = 99999;
    private static final int A_DMG3  = 99999;
    private static final int HITS = 3;
    private static final int A_HITS = 3;
    private static final int BLOCK1 = 4;
    private static final int A_BLOCK1 = 6;
    private static final int BLOCK2 = 7;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 1;
    private static final int A_MGC1 = 1;
    private static final int MGC2 = 2;
    private static final int A_MGC2 = 2;
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
    private int strength_num = 0 ;
    private int not_remove ;
    private boolean shark;
    public AbstractMonster[] uav = new AbstractMonster[3];
    public static  float[] POSX={-366.0F, -170.0F, -532.0F};
    public static  float[] POSY={-4.0F, 6.0F, 0.0F};
    float particleTimer;

    public MirageJudge() {
        this(0.0F, 0.0F);
    }
    public MirageJudge(float x, float y) {//Elite
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
        if (AbstractDungeon.ascensionLevel >= 18) {
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
        this.not_remove = 0;
        if (x < -500){
            this.flipHorizontal =true;
        }
    }

    public void usePreBattleAction() {

    }

    public void update() {
        super.update();
        if (!this.isDead) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer <= 0.0F) {
                this.particleTimer = 0.0125F;
                if (AbstractDungeon.getMonsters() != null && AbstractDungeon.getMonsters().monsters != null) {
                    for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                        if (mo != null && (mo instanceof Judge)) {
                            AbstractDungeon.effectsQueue.add(new QuietSpecialSmokeBombEffect(AbstractDungeon.cardRandomRng.random(mo.healthHb.x, mo.healthHb.x + mo.hb.width), mo.hb.y - 10F,
                                    0.5f, 0.6f, 0.0f, 0.2f, 0.5f, 0.7f));
                        }

                    }
                    AbstractDungeon.effectsQueue.add(new QuietSpecialSmokeBombEffect(AbstractDungeon.cardRandomRng.random(this.healthHb.x, this.healthHb.x + this.hb.width), this.hb.y - 10F,
                            0.5f, 0.6f, 0.0f, 0.2f, 0.5f, 0.7f));
                }
            }
        }
    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch(this.nextMove) {
            case 1:
                break ;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        this.setMove((byte) 1 ,Intent.NONE);
        if (turn_count==2){
            this.addToBot(new SuicideAction(this));
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


    public void damage (DamageInfo info) {
        super.damage(info);
        if (AbstractDungeon.getMonsters() != null && AbstractDungeon.getMonsters().monsters != null) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (mo != null && (mo instanceof Judge)) {
                    if (mo.moveName != null && MOVES != null && MOVES[2] != null && (mo.moveName.equals(MOVES[2])||mo.moveName.equals(MOVES[7]))) {
                        mo.setMove(MOVES[2], (byte) 2, Intent.BUFF);
                        mo.addToBot(new SetMoveAction(mo, MOVES[2], (byte) 2, Intent.BUFF));
                    }else if (mo.moveName != null && MOVES != null && MOVES[3] != null && (mo.moveName.equals(MOVES[3])||mo.moveName.equals(MOVES[8]))){
                        mo.setMove(MOVES[3],(byte) 3,Intent.BUFF);
                        mo.addToBot(new SetMoveAction(mo, MOVES[3], (byte) 3, Intent.BUFF));
                    }else if (mo.moveName != null && MOVES != null && MOVES[4] != null && (mo.moveName.equals(MOVES[4])||mo.moveName.equals(MOVES[9]))){
                        mo.setMove(MOVES[4],(byte) 4,Intent.ATTACK_DEBUFF,(mo.damage.get(0)).base);
                        mo.addToBot(new SetMoveAction(mo, MOVES[4], (byte) 4, Intent.ATTACK, (mo.damage.get(0)).base));
                    }else if (mo.moveName != null && MOVES != null && MOVES[5] != null && (mo.moveName.equals(MOVES[5])||mo.moveName.equals(MOVES[10]))){
                        mo.setMove(MOVES[5],(byte) 5,Intent.ATTACK_DEBUFF,(mo.damage.get(1)).base);
                        mo.addToBot(new SetMoveAction(mo, MOVES[5], (byte) 5, Intent.ATTACK, (mo.damage.get(1)).base));
                    }else if (mo.moveName != null && MOVES != null && MOVES[6] != null && (mo.moveName.equals(MOVES[6])||mo.moveName.equals(MOVES[11]))){
                        mo.setMove(MOVES[6], (byte) 6, Intent.ATTACK, (mo.damage.get(2)).base);
                        mo.addToBot(new SetMoveAction(mo, MOVES[6], (byte) 6, Intent.ATTACK, (mo.damage.get(2)).base));
                    }
                    mo.createIntent();
                    
                }
            }
        }
        
    }






}