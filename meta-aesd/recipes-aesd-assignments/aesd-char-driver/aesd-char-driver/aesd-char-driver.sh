#!/bin/sh

### BEGIN INIT INFO
# Provides:          aesd-char-driver driver
# Short-Description: aesd-char-driver Driver
# Description:       Start/stop the aesd-char-driver driver
### END INIT INFO

module="aesd-char-driver"
device="aesd-char-driver"
mode="664"
group="staff"

case "$1" in
    start)
        echo "Starting aesd-char-driver"
        modprobe aesdchar || exit 1
        major=$(awk "\$2==\"aesdchar\" {print \$1}" /proc/devices)
        rm -f /dev/aesdchar
        mknod /dev/aesdchar c $major 0
        chgrp $group /dev/aesdchar
        chmod $mode /dev/aesdchar
        ;;
    stop)
        echo "Stopping aesd-char-driver"
        rm -f /dev/aesdchar
        modprobe -r aesdchar || exit 1
        ;;
    restart)
        $0 stop
        $0 start
        ;;
    *)
        echo "Usage: $0 {start|stop|restart}"
        exit 1
        ;;
esac

exit 0