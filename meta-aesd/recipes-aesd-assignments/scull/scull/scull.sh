#!/bin/sh

### BEGIN INIT INFO
# Provides:          scull driver
# Short-Description: Scull Driver
# Description:       Start/stop the scull driver
### END INIT INFO

module="scull"
device="scull"
mode="664"
group="staff"

case "$1" in
    start)
        echo "Starting scull"
        modprobe scull || exit 1
        major=$(awk "\$2==\"scull\" {print \$1}" /proc/devices)
        rm -f /dev/scull[0-3]
        mknod /dev/scull0 c $major 0
        mknod /dev/scull1 c $major 1
        mknod /dev/scull2 c $major 2
        mknod /dev/scull3 c $major 3
        chgrp $group /dev/scull[0-3]
        chmod $mode /dev/scull[0-3]
        ;;
    stop)
        echo "Stopping scull"
        rm -f /dev/scull[0-3]
        modprobe -r scull || exit 1
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