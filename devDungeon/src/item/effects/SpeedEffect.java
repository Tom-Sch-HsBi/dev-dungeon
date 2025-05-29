package item.effects;

import core.Entity;
import systems.EventScheduler;
import core.components.VelocityComponent;

/**
 * Provides a mechanism to apply a temporary speed increase effect to an entity within the game.
 * Utilizing the EffectScheduler, this effect increases the entity's speed for a designated duration
 * before reverting it back to its original state. The implementation relies on scheduling both the
 * application of the speed increase and its subsequent reversal.
 */
public class SpeedEffect {
  private static final EventScheduler EVENT_SCHEDULER = EventScheduler.getInstance();
  private final float speedIncrease;
  private final int duration;

  /**
   * Initializes a new instance of the SpeedEffect with a specified increase in speed and duration.
   *
   * @param speedIncrease The amount to increase the entity's speed by.
   * @param duration The duration, in seconds, for which the speed increase is applied.
   */
  public SpeedEffect(float speedIncrease, int duration) {
    this.speedIncrease = speedIncrease;
    this.duration = duration;
  }

  /**
   * Applies a temporary speed increase to the target entity, then reverts its speed to normal after
   * the specified duration. The increase in speed is applied immediately, and its reversal will be
   * scheduled to occur after the duration expires.
   *
   * <p>TODO: Implement the applySpeedEffect method to schedule the speed increase and its
   * reversion.
   *
   * @param target The entity to which the speed effect will be applied.
   */
  public void applySpeedEffect(Entity target) {
      var velocityOpt = target.fetch(VelocityComponent.class);
      if (velocityOpt.isEmpty()) {
          throw new UnsupportedOperationException("SpeedEffect cant be apllied without VelocityComponent.");
      }

      VelocityComponent velocity = velocityOpt.get();

      // Speed wert wird auf die Velocity gerechnet
      float originalX = velocity.xVelocity();
      float originalY = velocity.yVelocity();

      // Setze neue Geschwindigkeit
      velocity.xVelocity(originalX + speedIncrease);
      velocity.yVelocity(originalY + speedIncrease);

      // Nach ablaufen der zeit wird der Effekt weggenomen
      EVENT_SCHEDULER.scheduleAction(() -> {
          velocity.xVelocity(originalX);
          velocity.yVelocity(originalY);
      }, duration * 1000L); // Zeit in millisek
  }
}
