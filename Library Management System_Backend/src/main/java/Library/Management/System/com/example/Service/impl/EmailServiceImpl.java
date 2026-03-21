package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Service.EmailSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailSerivce {

    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try{
            MineMessage mineMessage = javaMailSender.createMineMessage();
            MineMessageHelper helper = new MineMessageHelper(mineMessage);
            helper.setSubject(subject);
            helper.setText(body,true);
            javaMailSender.send(mineMessage);
        }catch(MailException e){
            MailException  e
        } catch (Exception e) {
            Throw new MailSendException("Failed to send Mail");
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
