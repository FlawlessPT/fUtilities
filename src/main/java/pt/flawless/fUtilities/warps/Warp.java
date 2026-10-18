package pt.flawless.fUtilities.warps;

public record Warp(String name, String world, double x, double y, double z, float yaw, float pitch,
                   String material, short data, boolean active, String group) {
}
