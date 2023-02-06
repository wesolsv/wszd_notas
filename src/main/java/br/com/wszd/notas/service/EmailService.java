package br.com.wszd.notas.service;


import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private NotaService notaService;
    @Autowired
    private AtividadeService atividadeService;

    public void enviarEmailNovoUsuario(Usuario usuario) {

        PessoaDTO pessoa = null;
        String toEmail = "";

        pessoa = pessoaService.pegarPessoaDTO(usuario.getPessoa().getId());
        toEmail = pessoa.getEmail();

        String subject = "Cadastro realizado com sucesso";
        String body = "Parabéns pelo cadastro em wszd-notas, seu usuário é " + usuario.getNomeUsuario();

        createEmail(toEmail, subject, body);
    }

    public void enviarEmailEdicaoUsuario(Usuario usuario) {

        PessoaDTO pessoa = null;
        String toEmail = "";

        pessoa = pessoaService.pegarPessoaDTO(usuario.getPessoa().getId());
        toEmail = pessoa.getEmail();

        String subject = "Edição de usuario com sucesso";
        String body = "Usuário Editado";

        createEmail(toEmail, subject, body);
    }

    public void enviarEmailNovaAtividade(Pessoa pessoa, Atividade atividade) {

        String toEmail = pessoaService.pegarPessoaDTO(pessoa.getId()).getEmail();

        String subject = "Atividade criada com sucesso!";
        String body = "Agora é só aguardar a notificação no dia "
                + atividade.getNome() + " data: " + atividade.getDataLembrete();

        createEmail(toEmail, subject, body);
    }

    private void createEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("noreplayjobboard@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        log.info("Email enviado com sucesso");
    }
}
