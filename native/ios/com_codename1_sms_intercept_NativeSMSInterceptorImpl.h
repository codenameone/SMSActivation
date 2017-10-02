#import <Foundation/Foundation.h>

@interface com_codename1_sms_intercept_NativeSMSInterceptorImpl : NSObject {
}

-(void)bindSMSListener;
-(void)unbindSMSListener;
-(BOOL)isSupported;
@end
