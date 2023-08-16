#Auto generated Octane revision tag
@TID19101REV0.13.0 @regressivo
Feature: Confluence Sucesso mobile

  Scenario: Pesquisar pagina mobile
    Given que acesso o Bitbucket mobile
    When pesquiso pelo repositorio mobile
    Then deve aparecer o repositorio pesquisado mobile