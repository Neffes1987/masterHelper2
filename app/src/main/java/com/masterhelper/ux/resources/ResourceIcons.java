package com.masterhelper.ux.resources;

import com.masterhelper.R;

/** Class for working with colors from resources */
public class ResourceIcons {
  public static int getIcon(ResourceColorType type){
    switch (type){
      case add: return R.mipmap.add;
      case done: return R.mipmap.done;
      case mace: return R.mipmap.mace;
      case play: return R.mipmap.play;
      case clear: return R.mipmap.clear_black_18dp;
      case flare: return R.mipmap.flare;
      case music: return R.mipmap.music;
      case pause: return R.mipmap.pause;
      case queue: return R.mipmap.queue;
      case enemy1: return R.mipmap.enemy1;
      case enemy2: return R.mipmap.enemy2;
      case enemy3: return R.mipmap.enemy3;
      case filter: return R.mipmap.filter;
      case pencil: return R.mipmap.pencil;
      case arrowUp:return R.mipmap.arrow_up;
      case forward:return R.mipmap.forward;
      case reorder:return R.mipmap.reorder;
      case favorite:return R.mipmap.favorite;
      case addCircle:return R.mipmap.add_circle;
      case arrowDown:return R.mipmap.arrow_down;
      case removeCircle: return R.mipmap.remove_circle;
      default:
        throw new Error("ResourceIcons - Wrong type of icon!");
    }
  };

  public enum ResourceColorType{
    add,
    addCircle,
    arrowDown,
    arrowUp,
    clear,
    done,
    enemy1,
    enemy2,
    enemy3,
    favorite,
    filter,
    flare,
    forward,
    mace,
    music,
    pause,
    pencil,
    play,
    queue,
    removeCircle,
    reorder
  }
}
