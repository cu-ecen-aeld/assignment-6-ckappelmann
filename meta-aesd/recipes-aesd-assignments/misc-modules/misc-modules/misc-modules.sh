#!/bin/sh

### BEGIN INIT INFO
# Provides:          misc-modules driver
# Short-Description: misc-modules Driver
# Description:       Start/stop the misc-modules driver
### END INIT INFO

module="misc-modules"

case "$1" in
    start)
        echo "Starting misc-modules"
        modprobe hello || exit 1
        insmod /lib/modules/*/extra/faulty.ko
        major=$(awk "\$2==\"faulty\" {print \$1}" /proc/devices)
        rm -f /dev/faulty
        mknod /dev/faulty c $major 0
        ;;
    stop)
        echo "Stopping misc-modules"
        rm -f /dev/faulty
        rmmod faulty || exit 1
        modprobe -r hello || exit 1
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