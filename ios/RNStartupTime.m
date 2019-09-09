#import "RNStartupTime.h"
#import <sys/sysctl.h>
#include <math.h>

@implementation RNStartupTime

RCT_EXPORT_MODULE()

static CFTimeInterval processStartTime() {
    size_t len = 4;
    int mib[len];
    struct kinfo_proc kp;
    
    sysctlnametomib("kern.proc.pid", mib, &len);
    mib[3] = getpid();
    len = sizeof(kp);
    sysctl(mib, 4, &kp, &len, NULL, 0);
    
    struct timeval startTime = kp.kp_proc.p_un.__p_starttime;
    
    CFTimeInterval absoluteTimeToRelativeTime =  CACurrentMediaTime() - [NSDate date].timeIntervalSince1970;
    return startTime.tv_sec + startTime.tv_usec / 1e6 + absoluteTimeToRelativeTime;
}

static CFTimeInterval timeMark;

+ (void)initialize {
    timeMark = processStartTime();
}

RCT_EXPORT_METHOD(getTimeSinceStartup:(RCTPromiseResolveBlock)resolve
                                       rejecter:(RCTPromiseRejectBlock)reject)
{
    CFTimeInterval diff = CACurrentMediaTime() - timeMark;
    resolve([NSNumber numberWithDouble:ceil(diff * 1000)]);
}

@end
