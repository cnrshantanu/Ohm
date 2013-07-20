package zakoi.livewallpaper.religious.shivsanker;
import aurelienribon.tweenengine.TweenAccessor;

public class RotationAccessor implements TweenAccessor<BackGroundController> {

    // The following lines define the different possible tween types.
    // It's up to you to define what you need :-)

    public static final int ROT_ANGLE = 1;
    
    // TweenAccessor implementation

    @Override
    public int getValues(BackGroundController target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ROT_ANGLE: returnValues[0] = target.getRotationAngle(); return 1;
            default: assert false; return -1;
        }
    }
    
    @Override
    public void setValues(BackGroundController target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ROT_ANGLE: target.setRotationAngle(newValues[0]); break;
            default: assert false; break;
        }
    }
}