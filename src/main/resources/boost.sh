#!/bin/bash
# Optional performance script for Linux users to run manually.
# This script tunes the system for better Minecraft stability.

echo "--- Minecraft Linux Performance Tuner ---"
echo "Note: This script should be run manually if you experience lag."

# Function to check for sudo availability
check_sudo() {
    if [ "$EUID" -ne 0 ]; then
        echo "Some optimizations require root. Please run with sudo if you want to apply all changes."
        return 1
    fi
    return 0
}

# Set CPU governor to performance
if [ -d /sys/devices/system/cpu/cpufreq ]; then
    echo "Setting CPU Governor to performance..."
    for i in /sys/devices/system/cpu/cpu*/cpufreq/scaling_governor; do
        [ -f "$i" ] && echo "performance" > "$i" 2>/dev/null
    done
fi

# Optimization: Increase file descriptor limit for large SMPs
ulimit -n 65536
echo "Increased file descriptor limit to 65536"

# Optimization: Suggest transparency for huge pages
if [ -f /sys/kernel/mm/transparent_hugepage/enabled ]; then
    echo "madvise" > /sys/kernel/mm/transparent_hugepage/enabled 2>/dev/null
    echo "Transparent hugepages set to madvise"
fi

echo "--- Tuning Complete ---"
