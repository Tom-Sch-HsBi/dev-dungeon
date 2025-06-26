package entities.levercommands;

import core.Game;
import core.level.elements.tile.PitTile;
import core.level.utils.Coordinate;
import utils.ICommand;

/**
 * The BridgeControlCommand class is responsible for controlling the bridge in the Bridge Guard
 * Riddle Level.
 *
 * <p>The bridge can be lowered and raised by executing and undoing this command.
 */
public class BridgeControlCommand implements ICommand {
  private final Coordinate topLeft;
  private final Coordinate bottomRight;

  /**
   * Creates a new BridgeControlCommand instance.
   *
   * @param topLeft The top left coordinate of the bridge.
   * @param bottomRight The bottom right coordinate of the bridge.
   */
  public BridgeControlCommand(Coordinate topLeft, Coordinate bottomRight) {
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
  }

  /**
   * Raises the bridge. By opening the pits, the bridge is raised.
   *
   * @see core.level.elements.tile.PitTile#open() PitTile.open
   * @see core.level.elements.ILevel#tilesInArea(Coordinate, Coordinate) tilesInArea
   */
  @Override
  public void execute() {
      Game.currentLevel()
          .tilesInArea(topLeft, bottomRight)
          .stream()
          .filter(tile -> tile instanceof PitTile)
          .map(tile -> (PitTile) tile)
          .forEach(PitTile::open);
  }

  /**
   * Lowers the bridge. By closing the pits, the bridge is lowered.
   *
   * @see core.level.elements.tile.PitTile#close() PitTile.close
   * @see core.level.elements.ILevel#tilesInArea(Coordinate, Coordinate) tilesInArea
   */
  @Override
  public void undo() {
      Game.currentLevel()
          .tilesInArea(topLeft, bottomRight)
          .stream()
          .filter(tile -> tile instanceof PitTile)
          .map(tile -> (PitTile) tile)
          .forEach(PitTile::close);
  }
}
