#!/bin/bash
# System-level performance booster for Linux
echo "Optimizing system for Minecraft Performance..."

# Set CPU governor to performance if possible
if [ -f /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor ]; then
    for i in /sys/devices/system/cpu/cpu*/cpufreq/scaling_governor; do
        echo "performance" | sudo tee $i > /dev/null
    done
    echo "CPU Governor set to Performance"
fi

# Increase open file limit for SMPs
ulimit -n 65536
echo "File descriptor limit increased"

# Clear swap memory to prevent stutter
sudo swapoff -a && sudo swapon -a
echo "Memory buffer refreshed"
